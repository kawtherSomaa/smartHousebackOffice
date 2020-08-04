package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.BathRoom;
import com.mycompany.myapp.repository.BathRoomRepository;
import com.mycompany.myapp.service.BathRoomService;
import com.mycompany.myapp.service.dto.BathRoomDTO;
import com.mycompany.myapp.service.mapper.BathRoomMapper;
import com.mycompany.myapp.service.dto.BathRoomCriteria;
import com.mycompany.myapp.service.BathRoomQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BathRoomResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BathRoomResourceIT {

    private static final String DEFAULT_LIBELLE_BATH_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_BATH_ROOM = "BBBBBBBBBB";

    @Autowired
    private BathRoomRepository bathRoomRepository;

    @Autowired
    private BathRoomMapper bathRoomMapper;

    @Autowired
    private BathRoomService bathRoomService;

    @Autowired
    private BathRoomQueryService bathRoomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBathRoomMockMvc;

    private BathRoom bathRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BathRoom createEntity(EntityManager em) {
        BathRoom bathRoom = new BathRoom()
            .libelleBathRoom(DEFAULT_LIBELLE_BATH_ROOM);
        return bathRoom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BathRoom createUpdatedEntity(EntityManager em) {
        BathRoom bathRoom = new BathRoom()
            .libelleBathRoom(UPDATED_LIBELLE_BATH_ROOM);
        return bathRoom;
    }

    @BeforeEach
    public void initTest() {
        bathRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createBathRoom() throws Exception {
        int databaseSizeBeforeCreate = bathRoomRepository.findAll().size();
        // Create the BathRoom
        BathRoomDTO bathRoomDTO = bathRoomMapper.toDto(bathRoom);
        restBathRoomMockMvc.perform(post("/api/bath-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bathRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the BathRoom in the database
        List<BathRoom> bathRoomList = bathRoomRepository.findAll();
        assertThat(bathRoomList).hasSize(databaseSizeBeforeCreate + 1);
        BathRoom testBathRoom = bathRoomList.get(bathRoomList.size() - 1);
        assertThat(testBathRoom.getLibelleBathRoom()).isEqualTo(DEFAULT_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void createBathRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bathRoomRepository.findAll().size();

        // Create the BathRoom with an existing ID
        bathRoom.setId(1L);
        BathRoomDTO bathRoomDTO = bathRoomMapper.toDto(bathRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBathRoomMockMvc.perform(post("/api/bath-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bathRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BathRoom in the database
        List<BathRoom> bathRoomList = bathRoomRepository.findAll();
        assertThat(bathRoomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBathRooms() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList
        restBathRoomMockMvc.perform(get("/api/bath-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bathRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleBathRoom").value(hasItem(DEFAULT_LIBELLE_BATH_ROOM)));
    }
    
    @Test
    @Transactional
    public void getBathRoom() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get the bathRoom
        restBathRoomMockMvc.perform(get("/api/bath-rooms/{id}", bathRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bathRoom.getId().intValue()))
            .andExpect(jsonPath("$.libelleBathRoom").value(DEFAULT_LIBELLE_BATH_ROOM));
    }


    @Test
    @Transactional
    public void getBathRoomsByIdFiltering() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        Long id = bathRoom.getId();

        defaultBathRoomShouldBeFound("id.equals=" + id);
        defaultBathRoomShouldNotBeFound("id.notEquals=" + id);

        defaultBathRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBathRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultBathRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBathRoomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom equals to DEFAULT_LIBELLE_BATH_ROOM
        defaultBathRoomShouldBeFound("libelleBathRoom.equals=" + DEFAULT_LIBELLE_BATH_ROOM);

        // Get all the bathRoomList where libelleBathRoom equals to UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldNotBeFound("libelleBathRoom.equals=" + UPDATED_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom not equals to DEFAULT_LIBELLE_BATH_ROOM
        defaultBathRoomShouldNotBeFound("libelleBathRoom.notEquals=" + DEFAULT_LIBELLE_BATH_ROOM);

        // Get all the bathRoomList where libelleBathRoom not equals to UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldBeFound("libelleBathRoom.notEquals=" + UPDATED_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomIsInShouldWork() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom in DEFAULT_LIBELLE_BATH_ROOM or UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldBeFound("libelleBathRoom.in=" + DEFAULT_LIBELLE_BATH_ROOM + "," + UPDATED_LIBELLE_BATH_ROOM);

        // Get all the bathRoomList where libelleBathRoom equals to UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldNotBeFound("libelleBathRoom.in=" + UPDATED_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom is not null
        defaultBathRoomShouldBeFound("libelleBathRoom.specified=true");

        // Get all the bathRoomList where libelleBathRoom is null
        defaultBathRoomShouldNotBeFound("libelleBathRoom.specified=false");
    }
                @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomContainsSomething() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom contains DEFAULT_LIBELLE_BATH_ROOM
        defaultBathRoomShouldBeFound("libelleBathRoom.contains=" + DEFAULT_LIBELLE_BATH_ROOM);

        // Get all the bathRoomList where libelleBathRoom contains UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldNotBeFound("libelleBathRoom.contains=" + UPDATED_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void getAllBathRoomsByLibelleBathRoomNotContainsSomething() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        // Get all the bathRoomList where libelleBathRoom does not contain DEFAULT_LIBELLE_BATH_ROOM
        defaultBathRoomShouldNotBeFound("libelleBathRoom.doesNotContain=" + DEFAULT_LIBELLE_BATH_ROOM);

        // Get all the bathRoomList where libelleBathRoom does not contain UPDATED_LIBELLE_BATH_ROOM
        defaultBathRoomShouldBeFound("libelleBathRoom.doesNotContain=" + UPDATED_LIBELLE_BATH_ROOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBathRoomShouldBeFound(String filter) throws Exception {
        restBathRoomMockMvc.perform(get("/api/bath-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bathRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleBathRoom").value(hasItem(DEFAULT_LIBELLE_BATH_ROOM)));

        // Check, that the count call also returns 1
        restBathRoomMockMvc.perform(get("/api/bath-rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBathRoomShouldNotBeFound(String filter) throws Exception {
        restBathRoomMockMvc.perform(get("/api/bath-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBathRoomMockMvc.perform(get("/api/bath-rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBathRoom() throws Exception {
        // Get the bathRoom
        restBathRoomMockMvc.perform(get("/api/bath-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBathRoom() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        int databaseSizeBeforeUpdate = bathRoomRepository.findAll().size();

        // Update the bathRoom
        BathRoom updatedBathRoom = bathRoomRepository.findById(bathRoom.getId()).get();
        // Disconnect from session so that the updates on updatedBathRoom are not directly saved in db
        em.detach(updatedBathRoom);
        updatedBathRoom
            .libelleBathRoom(UPDATED_LIBELLE_BATH_ROOM);
        BathRoomDTO bathRoomDTO = bathRoomMapper.toDto(updatedBathRoom);

        restBathRoomMockMvc.perform(put("/api/bath-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bathRoomDTO)))
            .andExpect(status().isOk());

        // Validate the BathRoom in the database
        List<BathRoom> bathRoomList = bathRoomRepository.findAll();
        assertThat(bathRoomList).hasSize(databaseSizeBeforeUpdate);
        BathRoom testBathRoom = bathRoomList.get(bathRoomList.size() - 1);
        assertThat(testBathRoom.getLibelleBathRoom()).isEqualTo(UPDATED_LIBELLE_BATH_ROOM);
    }

    @Test
    @Transactional
    public void updateNonExistingBathRoom() throws Exception {
        int databaseSizeBeforeUpdate = bathRoomRepository.findAll().size();

        // Create the BathRoom
        BathRoomDTO bathRoomDTO = bathRoomMapper.toDto(bathRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBathRoomMockMvc.perform(put("/api/bath-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bathRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BathRoom in the database
        List<BathRoom> bathRoomList = bathRoomRepository.findAll();
        assertThat(bathRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBathRoom() throws Exception {
        // Initialize the database
        bathRoomRepository.saveAndFlush(bathRoom);

        int databaseSizeBeforeDelete = bathRoomRepository.findAll().size();

        // Delete the bathRoom
        restBathRoomMockMvc.perform(delete("/api/bath-rooms/{id}", bathRoom.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BathRoom> bathRoomList = bathRoomRepository.findAll();
        assertThat(bathRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
