package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.Door;
import com.mycompany.myapp.repository.DoorRepository;
import com.mycompany.myapp.service.DoorService;
import com.mycompany.myapp.service.dto.DoorDTO;
import com.mycompany.myapp.service.mapper.DoorMapper;
import com.mycompany.myapp.service.dto.DoorCriteria;
import com.mycompany.myapp.service.DoorQueryService;

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
 * Integration tests for the {@link DoorResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DoorResourceIT {

    private static final String DEFAULT_LIBELLE_DOOR = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DOOR = "BBBBBBBBBB";

    @Autowired
    private DoorRepository doorRepository;

    @Autowired
    private DoorMapper doorMapper;

    @Autowired
    private DoorService doorService;

    @Autowired
    private DoorQueryService doorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoorMockMvc;

    private Door door;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Door createEntity(EntityManager em) {
        Door door = new Door()
            .libelleDoor(DEFAULT_LIBELLE_DOOR);
        return door;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Door createUpdatedEntity(EntityManager em) {
        Door door = new Door()
            .libelleDoor(UPDATED_LIBELLE_DOOR);
        return door;
    }

    @BeforeEach
    public void initTest() {
        door = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoor() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();
        // Create the Door
        DoorDTO doorDTO = doorMapper.toDto(door);
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isCreated());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate + 1);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getLibelleDoor()).isEqualTo(DEFAULT_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void createDoorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doorRepository.findAll().size();

        // Create the Door with an existing ID
        door.setId(1L);
        DoorDTO doorDTO = doorMapper.toDto(door);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoorMockMvc.perform(post("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoors() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList
        restDoorMockMvc.perform(get("/api/doors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(door.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDoor").value(hasItem(DEFAULT_LIBELLE_DOOR)));
    }
    
    @Test
    @Transactional
    public void getDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", door.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(door.getId().intValue()))
            .andExpect(jsonPath("$.libelleDoor").value(DEFAULT_LIBELLE_DOOR));
    }


    @Test
    @Transactional
    public void getDoorsByIdFiltering() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        Long id = door.getId();

        defaultDoorShouldBeFound("id.equals=" + id);
        defaultDoorShouldNotBeFound("id.notEquals=" + id);

        defaultDoorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDoorShouldNotBeFound("id.greaterThan=" + id);

        defaultDoorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDoorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDoorsByLibelleDoorIsEqualToSomething() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor equals to DEFAULT_LIBELLE_DOOR
        defaultDoorShouldBeFound("libelleDoor.equals=" + DEFAULT_LIBELLE_DOOR);

        // Get all the doorList where libelleDoor equals to UPDATED_LIBELLE_DOOR
        defaultDoorShouldNotBeFound("libelleDoor.equals=" + UPDATED_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void getAllDoorsByLibelleDoorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor not equals to DEFAULT_LIBELLE_DOOR
        defaultDoorShouldNotBeFound("libelleDoor.notEquals=" + DEFAULT_LIBELLE_DOOR);

        // Get all the doorList where libelleDoor not equals to UPDATED_LIBELLE_DOOR
        defaultDoorShouldBeFound("libelleDoor.notEquals=" + UPDATED_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void getAllDoorsByLibelleDoorIsInShouldWork() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor in DEFAULT_LIBELLE_DOOR or UPDATED_LIBELLE_DOOR
        defaultDoorShouldBeFound("libelleDoor.in=" + DEFAULT_LIBELLE_DOOR + "," + UPDATED_LIBELLE_DOOR);

        // Get all the doorList where libelleDoor equals to UPDATED_LIBELLE_DOOR
        defaultDoorShouldNotBeFound("libelleDoor.in=" + UPDATED_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void getAllDoorsByLibelleDoorIsNullOrNotNull() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor is not null
        defaultDoorShouldBeFound("libelleDoor.specified=true");

        // Get all the doorList where libelleDoor is null
        defaultDoorShouldNotBeFound("libelleDoor.specified=false");
    }
                @Test
    @Transactional
    public void getAllDoorsByLibelleDoorContainsSomething() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor contains DEFAULT_LIBELLE_DOOR
        defaultDoorShouldBeFound("libelleDoor.contains=" + DEFAULT_LIBELLE_DOOR);

        // Get all the doorList where libelleDoor contains UPDATED_LIBELLE_DOOR
        defaultDoorShouldNotBeFound("libelleDoor.contains=" + UPDATED_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void getAllDoorsByLibelleDoorNotContainsSomething() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        // Get all the doorList where libelleDoor does not contain DEFAULT_LIBELLE_DOOR
        defaultDoorShouldNotBeFound("libelleDoor.doesNotContain=" + DEFAULT_LIBELLE_DOOR);

        // Get all the doorList where libelleDoor does not contain UPDATED_LIBELLE_DOOR
        defaultDoorShouldBeFound("libelleDoor.doesNotContain=" + UPDATED_LIBELLE_DOOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDoorShouldBeFound(String filter) throws Exception {
        restDoorMockMvc.perform(get("/api/doors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(door.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDoor").value(hasItem(DEFAULT_LIBELLE_DOOR)));

        // Check, that the count call also returns 1
        restDoorMockMvc.perform(get("/api/doors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDoorShouldNotBeFound(String filter) throws Exception {
        restDoorMockMvc.perform(get("/api/doors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDoorMockMvc.perform(get("/api/doors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDoor() throws Exception {
        // Get the door
        restDoorMockMvc.perform(get("/api/doors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Update the door
        Door updatedDoor = doorRepository.findById(door.getId()).get();
        // Disconnect from session so that the updates on updatedDoor are not directly saved in db
        em.detach(updatedDoor);
        updatedDoor
            .libelleDoor(UPDATED_LIBELLE_DOOR);
        DoorDTO doorDTO = doorMapper.toDto(updatedDoor);

        restDoorMockMvc.perform(put("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isOk());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
        Door testDoor = doorList.get(doorList.size() - 1);
        assertThat(testDoor.getLibelleDoor()).isEqualTo(UPDATED_LIBELLE_DOOR);
    }

    @Test
    @Transactional
    public void updateNonExistingDoor() throws Exception {
        int databaseSizeBeforeUpdate = doorRepository.findAll().size();

        // Create the Door
        DoorDTO doorDTO = doorMapper.toDto(door);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoorMockMvc.perform(put("/api/doors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Door in the database
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoor() throws Exception {
        // Initialize the database
        doorRepository.saveAndFlush(door);

        int databaseSizeBeforeDelete = doorRepository.findAll().size();

        // Delete the door
        restDoorMockMvc.perform(delete("/api/doors/{id}", door.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Door> doorList = doorRepository.findAll();
        assertThat(doorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
