package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.Room;
import com.mycompany.myapp.repository.RoomRepository;
import com.mycompany.myapp.service.RoomService;
import com.mycompany.myapp.service.dto.RoomDTO;
import com.mycompany.myapp.service.mapper.RoomMapper;
import com.mycompany.myapp.service.dto.RoomCriteria;
import com.mycompany.myapp.service.RoomQueryService;

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
 * Integration tests for the {@link RoomResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RoomResourceIT {

    private static final String DEFAULT_LIBELLE_ROOM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ROOM = "BBBBBBBBBB";

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomQueryService roomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .libelleRoom(DEFAULT_LIBELLE_ROOM);
        return room;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .libelleRoom(UPDATED_LIBELLE_ROOM);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();
        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getLibelleRoom()).isEqualTo(DEFAULT_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void createRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room with an existing ID
        room.setId(1L);
        RoomDTO roomDTO = roomMapper.toDto(room);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleRoom").value(hasItem(DEFAULT_LIBELLE_ROOM)));
    }
    
    @Test
    @Transactional
    public void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.libelleRoom").value(DEFAULT_LIBELLE_ROOM));
    }


    @Test
    @Transactional
    public void getRoomsByIdFiltering() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        Long id = room.getId();

        defaultRoomShouldBeFound("id.equals=" + id);
        defaultRoomShouldNotBeFound("id.notEquals=" + id);

        defaultRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRoomsByLibelleRoomIsEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom equals to DEFAULT_LIBELLE_ROOM
        defaultRoomShouldBeFound("libelleRoom.equals=" + DEFAULT_LIBELLE_ROOM);

        // Get all the roomList where libelleRoom equals to UPDATED_LIBELLE_ROOM
        defaultRoomShouldNotBeFound("libelleRoom.equals=" + UPDATED_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void getAllRoomsByLibelleRoomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom not equals to DEFAULT_LIBELLE_ROOM
        defaultRoomShouldNotBeFound("libelleRoom.notEquals=" + DEFAULT_LIBELLE_ROOM);

        // Get all the roomList where libelleRoom not equals to UPDATED_LIBELLE_ROOM
        defaultRoomShouldBeFound("libelleRoom.notEquals=" + UPDATED_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void getAllRoomsByLibelleRoomIsInShouldWork() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom in DEFAULT_LIBELLE_ROOM or UPDATED_LIBELLE_ROOM
        defaultRoomShouldBeFound("libelleRoom.in=" + DEFAULT_LIBELLE_ROOM + "," + UPDATED_LIBELLE_ROOM);

        // Get all the roomList where libelleRoom equals to UPDATED_LIBELLE_ROOM
        defaultRoomShouldNotBeFound("libelleRoom.in=" + UPDATED_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void getAllRoomsByLibelleRoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom is not null
        defaultRoomShouldBeFound("libelleRoom.specified=true");

        // Get all the roomList where libelleRoom is null
        defaultRoomShouldNotBeFound("libelleRoom.specified=false");
    }
                @Test
    @Transactional
    public void getAllRoomsByLibelleRoomContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom contains DEFAULT_LIBELLE_ROOM
        defaultRoomShouldBeFound("libelleRoom.contains=" + DEFAULT_LIBELLE_ROOM);

        // Get all the roomList where libelleRoom contains UPDATED_LIBELLE_ROOM
        defaultRoomShouldNotBeFound("libelleRoom.contains=" + UPDATED_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void getAllRoomsByLibelleRoomNotContainsSomething() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList where libelleRoom does not contain DEFAULT_LIBELLE_ROOM
        defaultRoomShouldNotBeFound("libelleRoom.doesNotContain=" + DEFAULT_LIBELLE_ROOM);

        // Get all the roomList where libelleRoom does not contain UPDATED_LIBELLE_ROOM
        defaultRoomShouldBeFound("libelleRoom.doesNotContain=" + UPDATED_LIBELLE_ROOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoomShouldBeFound(String filter) throws Exception {
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleRoom").value(hasItem(DEFAULT_LIBELLE_ROOM)));

        // Check, that the count call also returns 1
        restRoomMockMvc.perform(get("/api/rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoomShouldNotBeFound(String filter) throws Exception {
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoomMockMvc.perform(get("/api/rooms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .libelleRoom(UPDATED_LIBELLE_ROOM);
        RoomDTO roomDTO = roomMapper.toDto(updatedRoom);

        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getLibelleRoom()).isEqualTo(UPDATED_LIBELLE_ROOM);
    }

    @Test
    @Transactional
    public void updateNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc.perform(delete("/api/rooms/{id}", room.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
