package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.House;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.LivingRoom;
import com.mycompany.myapp.domain.Door;
import com.mycompany.myapp.domain.BathRoom;
import com.mycompany.myapp.domain.Kitchen;
import com.mycompany.myapp.domain.Room;
import com.mycompany.myapp.repository.HouseRepository;
import com.mycompany.myapp.service.HouseService;
import com.mycompany.myapp.service.dto.HouseDTO;
import com.mycompany.myapp.service.mapper.HouseMapper;
import com.mycompany.myapp.service.dto.HouseCriteria;
import com.mycompany.myapp.service.HouseQueryService;

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
 * Integration tests for the {@link HouseResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HouseResourceIT {

    private static final String DEFAULT_LIBELLE_HOUSE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_HOUSE = "BBBBBBBBBB";

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseQueryService houseQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHouseMockMvc;

    private House house;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static House createEntity(EntityManager em) {
        House house = new House()
            .libelleHouse(DEFAULT_LIBELLE_HOUSE);
        return house;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static House createUpdatedEntity(EntityManager em) {
        House house = new House()
            .libelleHouse(UPDATED_LIBELLE_HOUSE);
        return house;
    }

    @BeforeEach
    public void initTest() {
        house = createEntity(em);
    }

    @Test
    @Transactional
    public void createHouse() throws Exception {
        int databaseSizeBeforeCreate = houseRepository.findAll().size();
        // Create the House
        HouseDTO houseDTO = houseMapper.toDto(house);
        restHouseMockMvc.perform(post("/api/houses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isCreated());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeCreate + 1);
        House testHouse = houseList.get(houseList.size() - 1);
        assertThat(testHouse.getLibelleHouse()).isEqualTo(DEFAULT_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void createHouseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = houseRepository.findAll().size();

        // Create the House with an existing ID
        house.setId(1L);
        HouseDTO houseDTO = houseMapper.toDto(house);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHouseMockMvc.perform(post("/api/houses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHouses() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList
        restHouseMockMvc.perform(get("/api/houses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(house.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleHouse").value(hasItem(DEFAULT_LIBELLE_HOUSE)));
    }
    
    @Test
    @Transactional
    public void getHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get the house
        restHouseMockMvc.perform(get("/api/houses/{id}", house.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(house.getId().intValue()))
            .andExpect(jsonPath("$.libelleHouse").value(DEFAULT_LIBELLE_HOUSE));
    }


    @Test
    @Transactional
    public void getHousesByIdFiltering() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        Long id = house.getId();

        defaultHouseShouldBeFound("id.equals=" + id);
        defaultHouseShouldNotBeFound("id.notEquals=" + id);

        defaultHouseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHouseShouldNotBeFound("id.greaterThan=" + id);

        defaultHouseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHouseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHousesByLibelleHouseIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse equals to DEFAULT_LIBELLE_HOUSE
        defaultHouseShouldBeFound("libelleHouse.equals=" + DEFAULT_LIBELLE_HOUSE);

        // Get all the houseList where libelleHouse equals to UPDATED_LIBELLE_HOUSE
        defaultHouseShouldNotBeFound("libelleHouse.equals=" + UPDATED_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void getAllHousesByLibelleHouseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse not equals to DEFAULT_LIBELLE_HOUSE
        defaultHouseShouldNotBeFound("libelleHouse.notEquals=" + DEFAULT_LIBELLE_HOUSE);

        // Get all the houseList where libelleHouse not equals to UPDATED_LIBELLE_HOUSE
        defaultHouseShouldBeFound("libelleHouse.notEquals=" + UPDATED_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void getAllHousesByLibelleHouseIsInShouldWork() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse in DEFAULT_LIBELLE_HOUSE or UPDATED_LIBELLE_HOUSE
        defaultHouseShouldBeFound("libelleHouse.in=" + DEFAULT_LIBELLE_HOUSE + "," + UPDATED_LIBELLE_HOUSE);

        // Get all the houseList where libelleHouse equals to UPDATED_LIBELLE_HOUSE
        defaultHouseShouldNotBeFound("libelleHouse.in=" + UPDATED_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void getAllHousesByLibelleHouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse is not null
        defaultHouseShouldBeFound("libelleHouse.specified=true");

        // Get all the houseList where libelleHouse is null
        defaultHouseShouldNotBeFound("libelleHouse.specified=false");
    }
                @Test
    @Transactional
    public void getAllHousesByLibelleHouseContainsSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse contains DEFAULT_LIBELLE_HOUSE
        defaultHouseShouldBeFound("libelleHouse.contains=" + DEFAULT_LIBELLE_HOUSE);

        // Get all the houseList where libelleHouse contains UPDATED_LIBELLE_HOUSE
        defaultHouseShouldNotBeFound("libelleHouse.contains=" + UPDATED_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void getAllHousesByLibelleHouseNotContainsSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        // Get all the houseList where libelleHouse does not contain DEFAULT_LIBELLE_HOUSE
        defaultHouseShouldNotBeFound("libelleHouse.doesNotContain=" + DEFAULT_LIBELLE_HOUSE);

        // Get all the houseList where libelleHouse does not contain UPDATED_LIBELLE_HOUSE
        defaultHouseShouldBeFound("libelleHouse.doesNotContain=" + UPDATED_LIBELLE_HOUSE);
    }


    @Test
    @Transactional
    public void getAllHousesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        house.setUser(user);
        houseRepository.saveAndFlush(house);
        Long userId = user.getId();

        // Get all the houseList where user equals to userId
        defaultHouseShouldBeFound("userId.equals=" + userId);

        // Get all the houseList where user equals to userId + 1
        defaultHouseShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllHousesByLivingroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        LivingRoom livingrooms = LivingRoomResourceIT.createEntity(em);
        em.persist(livingrooms);
        em.flush();
        house.setLivingrooms(livingrooms);
        houseRepository.saveAndFlush(house);
        Long livingroomsId = livingrooms.getId();

        // Get all the houseList where livingrooms equals to livingroomsId
        defaultHouseShouldBeFound("livingroomsId.equals=" + livingroomsId);

        // Get all the houseList where livingrooms equals to livingroomsId + 1
        defaultHouseShouldNotBeFound("livingroomsId.equals=" + (livingroomsId + 1));
    }


    @Test
    @Transactional
    public void getAllHousesByDoorsIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        Door doors = DoorResourceIT.createEntity(em);
        em.persist(doors);
        em.flush();
        house.setDoors(doors);
        houseRepository.saveAndFlush(house);
        Long doorsId = doors.getId();

        // Get all the houseList where doors equals to doorsId
        defaultHouseShouldBeFound("doorsId.equals=" + doorsId);

        // Get all the houseList where doors equals to doorsId + 1
        defaultHouseShouldNotBeFound("doorsId.equals=" + (doorsId + 1));
    }


    @Test
    @Transactional
    public void getAllHousesByBathroomsIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        BathRoom bathrooms = BathRoomResourceIT.createEntity(em);
        em.persist(bathrooms);
        em.flush();
        house.setBathrooms(bathrooms);
        houseRepository.saveAndFlush(house);
        Long bathroomsId = bathrooms.getId();

        // Get all the houseList where bathrooms equals to bathroomsId
        defaultHouseShouldBeFound("bathroomsId.equals=" + bathroomsId);

        // Get all the houseList where bathrooms equals to bathroomsId + 1
        defaultHouseShouldNotBeFound("bathroomsId.equals=" + (bathroomsId + 1));
    }


    @Test
    @Transactional
    public void getAllHousesByKitchensIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        Kitchen kitchens = KitchenResourceIT.createEntity(em);
        em.persist(kitchens);
        em.flush();
        house.setKitchens(kitchens);
        houseRepository.saveAndFlush(house);
        Long kitchensId = kitchens.getId();

        // Get all the houseList where kitchens equals to kitchensId
        defaultHouseShouldBeFound("kitchensId.equals=" + kitchensId);

        // Get all the houseList where kitchens equals to kitchensId + 1
        defaultHouseShouldNotBeFound("kitchensId.equals=" + (kitchensId + 1));
    }


    @Test
    @Transactional
    public void getAllHousesByRoomsIsEqualToSomething() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);
        Room rooms = RoomResourceIT.createEntity(em);
        em.persist(rooms);
        em.flush();
        house.setRooms(rooms);
        houseRepository.saveAndFlush(house);
        Long roomsId = rooms.getId();

        // Get all the houseList where rooms equals to roomsId
        defaultHouseShouldBeFound("roomsId.equals=" + roomsId);

        // Get all the houseList where rooms equals to roomsId + 1
        defaultHouseShouldNotBeFound("roomsId.equals=" + (roomsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHouseShouldBeFound(String filter) throws Exception {
        restHouseMockMvc.perform(get("/api/houses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(house.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleHouse").value(hasItem(DEFAULT_LIBELLE_HOUSE)));

        // Check, that the count call also returns 1
        restHouseMockMvc.perform(get("/api/houses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHouseShouldNotBeFound(String filter) throws Exception {
        restHouseMockMvc.perform(get("/api/houses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHouseMockMvc.perform(get("/api/houses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHouse() throws Exception {
        // Get the house
        restHouseMockMvc.perform(get("/api/houses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        int databaseSizeBeforeUpdate = houseRepository.findAll().size();

        // Update the house
        House updatedHouse = houseRepository.findById(house.getId()).get();
        // Disconnect from session so that the updates on updatedHouse are not directly saved in db
        em.detach(updatedHouse);
        updatedHouse
            .libelleHouse(UPDATED_LIBELLE_HOUSE);
        HouseDTO houseDTO = houseMapper.toDto(updatedHouse);

        restHouseMockMvc.perform(put("/api/houses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isOk());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeUpdate);
        House testHouse = houseList.get(houseList.size() - 1);
        assertThat(testHouse.getLibelleHouse()).isEqualTo(UPDATED_LIBELLE_HOUSE);
    }

    @Test
    @Transactional
    public void updateNonExistingHouse() throws Exception {
        int databaseSizeBeforeUpdate = houseRepository.findAll().size();

        // Create the House
        HouseDTO houseDTO = houseMapper.toDto(house);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHouseMockMvc.perform(put("/api/houses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(houseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the House in the database
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHouse() throws Exception {
        // Initialize the database
        houseRepository.saveAndFlush(house);

        int databaseSizeBeforeDelete = houseRepository.findAll().size();

        // Delete the house
        restHouseMockMvc.perform(delete("/api/houses/{id}", house.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<House> houseList = houseRepository.findAll();
        assertThat(houseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
