package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.LivingRoom;
import com.mycompany.myapp.repository.LivingRoomRepository;
import com.mycompany.myapp.service.LivingRoomService;
import com.mycompany.myapp.service.dto.LivingRoomDTO;
import com.mycompany.myapp.service.mapper.LivingRoomMapper;
import com.mycompany.myapp.service.dto.LivingRoomCriteria;
import com.mycompany.myapp.service.LivingRoomQueryService;

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
 * Integration tests for the {@link LivingRoomResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LivingRoomResourceIT {

    private static final String DEFAULT_LIBELLE_LIVING_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_LIVING_ROOM = "BBBBBBBBBB";

    @Autowired
    private LivingRoomRepository livingRoomRepository;

    @Autowired
    private LivingRoomMapper livingRoomMapper;

    @Autowired
    private LivingRoomService livingRoomService;

    @Autowired
    private LivingRoomQueryService livingRoomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivingRoomMockMvc;

    private LivingRoom livingRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivingRoom createEntity(EntityManager em) {
        LivingRoom livingRoom = new LivingRoom()
            .libelleLivingRoom(DEFAULT_LIBELLE_LIVING_ROOM);
        return livingRoom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LivingRoom createUpdatedEntity(EntityManager em) {
        LivingRoom livingRoom = new LivingRoom()
            .libelleLivingRoom(UPDATED_LIBELLE_LIVING_ROOM);
        return livingRoom;
    }

    @BeforeEach
    public void initTest() {
        livingRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivingRoom() throws Exception {
        int databaseSizeBeforeCreate = livingRoomRepository.findAll().size();
        // Create the LivingRoom
        LivingRoomDTO livingRoomDTO = livingRoomMapper.toDto(livingRoom);
        restLivingRoomMockMvc.perform(post("/api/living-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livingRoomDTO)))
            .andExpect(status().isCreated());

        // Validate the LivingRoom in the database
        List<LivingRoom> livingRoomList = livingRoomRepository.findAll();
        assertThat(livingRoomList).hasSize(databaseSizeBeforeCreate + 1);
        LivingRoom testLivingRoom = livingRoomList.get(livingRoomList.size() - 1);
        assertThat(testLivingRoom.getLibelleLivingRoom()).isEqualTo(DEFAULT_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void createLivingRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livingRoomRepository.findAll().size();

        // Create the LivingRoom with an existing ID
        livingRoom.setId(1L);
        LivingRoomDTO livingRoomDTO = livingRoomMapper.toDto(livingRoom);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivingRoomMockMvc.perform(post("/api/living-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livingRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivingRoom in the database
        List<LivingRoom> livingRoomList = livingRoomRepository.findAll();
        assertThat(livingRoomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLivingRooms() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList
        restLivingRoomMockMvc.perform(get("/api/living-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livingRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleLivingRoom").value(hasItem(DEFAULT_LIBELLE_LIVING_ROOM)));
    }
    
    @Test
    @Transactional
    public void getLivingRoom() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get the livingRoom
        restLivingRoomMockMvc.perform(get("/api/living-rooms/{id}", livingRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livingRoom.getId().intValue()))
            .andExpect(jsonPath("$.libelleLivingRoom").value(DEFAULT_LIBELLE_LIVING_ROOM));
    }


    @Test
    @Transactional
    public void getLivingRoomsByIdFiltering() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        Long id = livingRoom.getId();

        defaultLivingRoomShouldBeFound("id.equals=" + id);
        defaultLivingRoomShouldNotBeFound("id.notEquals=" + id);

        defaultLivingRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLivingRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultLivingRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLivingRoomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom equals to DEFAULT_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldBeFound("libelleLivingRoom.equals=" + DEFAULT_LIBELLE_LIVING_ROOM);

        // Get all the livingRoomList where libelleLivingRoom equals to UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.equals=" + UPDATED_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom not equals to DEFAULT_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.notEquals=" + DEFAULT_LIBELLE_LIVING_ROOM);

        // Get all the livingRoomList where libelleLivingRoom not equals to UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldBeFound("libelleLivingRoom.notEquals=" + UPDATED_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomIsInShouldWork() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom in DEFAULT_LIBELLE_LIVING_ROOM or UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldBeFound("libelleLivingRoom.in=" + DEFAULT_LIBELLE_LIVING_ROOM + "," + UPDATED_LIBELLE_LIVING_ROOM);

        // Get all the livingRoomList where libelleLivingRoom equals to UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.in=" + UPDATED_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom is not null
        defaultLivingRoomShouldBeFound("libelleLivingRoom.specified=true");

        // Get all the livingRoomList where libelleLivingRoom is null
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.specified=false");
    }
                @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomContainsSomething() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom contains DEFAULT_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldBeFound("libelleLivingRoom.contains=" + DEFAULT_LIBELLE_LIVING_ROOM);

        // Get all the livingRoomList where libelleLivingRoom contains UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.contains=" + UPDATED_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void getAllLivingRoomsByLibelleLivingRoomNotContainsSomething() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        // Get all the livingRoomList where libelleLivingRoom does not contain DEFAULT_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldNotBeFound("libelleLivingRoom.doesNotContain=" + DEFAULT_LIBELLE_LIVING_ROOM);

        // Get all the livingRoomList where libelleLivingRoom does not contain UPDATED_LIBELLE_LIVING_ROOM
        defaultLivingRoomShouldBeFound("libelleLivingRoom.doesNotContain=" + UPDATED_LIBELLE_LIVING_ROOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLivingRoomShouldBeFound(String filter) throws Exception {
        restLivingRoomMockMvc.perform(get("/api/living-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livingRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleLivingRoom").value(hasItem(DEFAULT_LIBELLE_LIVING_ROOM)));

        // Check, that the count call also returns 1
        restLivingRoomMockMvc.perform(get("/api/living-rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLivingRoomShouldNotBeFound(String filter) throws Exception {
        restLivingRoomMockMvc.perform(get("/api/living-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLivingRoomMockMvc.perform(get("/api/living-rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLivingRoom() throws Exception {
        // Get the livingRoom
        restLivingRoomMockMvc.perform(get("/api/living-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivingRoom() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        int databaseSizeBeforeUpdate = livingRoomRepository.findAll().size();

        // Update the livingRoom
        LivingRoom updatedLivingRoom = livingRoomRepository.findById(livingRoom.getId()).get();
        // Disconnect from session so that the updates on updatedLivingRoom are not directly saved in db
        em.detach(updatedLivingRoom);
        updatedLivingRoom
            .libelleLivingRoom(UPDATED_LIBELLE_LIVING_ROOM);
        LivingRoomDTO livingRoomDTO = livingRoomMapper.toDto(updatedLivingRoom);

        restLivingRoomMockMvc.perform(put("/api/living-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livingRoomDTO)))
            .andExpect(status().isOk());

        // Validate the LivingRoom in the database
        List<LivingRoom> livingRoomList = livingRoomRepository.findAll();
        assertThat(livingRoomList).hasSize(databaseSizeBeforeUpdate);
        LivingRoom testLivingRoom = livingRoomList.get(livingRoomList.size() - 1);
        assertThat(testLivingRoom.getLibelleLivingRoom()).isEqualTo(UPDATED_LIBELLE_LIVING_ROOM);
    }

    @Test
    @Transactional
    public void updateNonExistingLivingRoom() throws Exception {
        int databaseSizeBeforeUpdate = livingRoomRepository.findAll().size();

        // Create the LivingRoom
        LivingRoomDTO livingRoomDTO = livingRoomMapper.toDto(livingRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivingRoomMockMvc.perform(put("/api/living-rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livingRoomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LivingRoom in the database
        List<LivingRoom> livingRoomList = livingRoomRepository.findAll();
        assertThat(livingRoomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivingRoom() throws Exception {
        // Initialize the database
        livingRoomRepository.saveAndFlush(livingRoom);

        int databaseSizeBeforeDelete = livingRoomRepository.findAll().size();

        // Delete the livingRoom
        restLivingRoomMockMvc.perform(delete("/api/living-rooms/{id}", livingRoom.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LivingRoom> livingRoomList = livingRoomRepository.findAll();
        assertThat(livingRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
