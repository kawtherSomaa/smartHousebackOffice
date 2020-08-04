package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SmartHousebackOfficeApp;
import com.mycompany.myapp.domain.Kitchen;
import com.mycompany.myapp.repository.KitchenRepository;
import com.mycompany.myapp.service.KitchenService;
import com.mycompany.myapp.service.dto.KitchenDTO;
import com.mycompany.myapp.service.mapper.KitchenMapper;
import com.mycompany.myapp.service.dto.KitchenCriteria;
import com.mycompany.myapp.service.KitchenQueryService;

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
 * Integration tests for the {@link KitchenResource} REST controller.
 */
@SpringBootTest(classes = SmartHousebackOfficeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KitchenResourceIT {

    private static final String DEFAULT_LIBELLE_KITCHEN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_KITCHEN = "BBBBBBBBBB";

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenMapper kitchenMapper;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenQueryService kitchenQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKitchenMockMvc;

    private Kitchen kitchen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kitchen createEntity(EntityManager em) {
        Kitchen kitchen = new Kitchen()
            .libelleKitchen(DEFAULT_LIBELLE_KITCHEN);
        return kitchen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kitchen createUpdatedEntity(EntityManager em) {
        Kitchen kitchen = new Kitchen()
            .libelleKitchen(UPDATED_LIBELLE_KITCHEN);
        return kitchen;
    }

    @BeforeEach
    public void initTest() {
        kitchen = createEntity(em);
    }

    @Test
    @Transactional
    public void createKitchen() throws Exception {
        int databaseSizeBeforeCreate = kitchenRepository.findAll().size();
        // Create the Kitchen
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);
        restKitchenMockMvc.perform(post("/api/kitchens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isCreated());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeCreate + 1);
        Kitchen testKitchen = kitchenList.get(kitchenList.size() - 1);
        assertThat(testKitchen.getLibelleKitchen()).isEqualTo(DEFAULT_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void createKitchenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kitchenRepository.findAll().size();

        // Create the Kitchen with an existing ID
        kitchen.setId(1L);
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKitchenMockMvc.perform(post("/api/kitchens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKitchens() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList
        restKitchenMockMvc.perform(get("/api/kitchens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kitchen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleKitchen").value(hasItem(DEFAULT_LIBELLE_KITCHEN)));
    }
    
    @Test
    @Transactional
    public void getKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get the kitchen
        restKitchenMockMvc.perform(get("/api/kitchens/{id}", kitchen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kitchen.getId().intValue()))
            .andExpect(jsonPath("$.libelleKitchen").value(DEFAULT_LIBELLE_KITCHEN));
    }


    @Test
    @Transactional
    public void getKitchensByIdFiltering() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        Long id = kitchen.getId();

        defaultKitchenShouldBeFound("id.equals=" + id);
        defaultKitchenShouldNotBeFound("id.notEquals=" + id);

        defaultKitchenShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKitchenShouldNotBeFound("id.greaterThan=" + id);

        defaultKitchenShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKitchenShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenIsEqualToSomething() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen equals to DEFAULT_LIBELLE_KITCHEN
        defaultKitchenShouldBeFound("libelleKitchen.equals=" + DEFAULT_LIBELLE_KITCHEN);

        // Get all the kitchenList where libelleKitchen equals to UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldNotBeFound("libelleKitchen.equals=" + UPDATED_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen not equals to DEFAULT_LIBELLE_KITCHEN
        defaultKitchenShouldNotBeFound("libelleKitchen.notEquals=" + DEFAULT_LIBELLE_KITCHEN);

        // Get all the kitchenList where libelleKitchen not equals to UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldBeFound("libelleKitchen.notEquals=" + UPDATED_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenIsInShouldWork() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen in DEFAULT_LIBELLE_KITCHEN or UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldBeFound("libelleKitchen.in=" + DEFAULT_LIBELLE_KITCHEN + "," + UPDATED_LIBELLE_KITCHEN);

        // Get all the kitchenList where libelleKitchen equals to UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldNotBeFound("libelleKitchen.in=" + UPDATED_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenIsNullOrNotNull() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen is not null
        defaultKitchenShouldBeFound("libelleKitchen.specified=true");

        // Get all the kitchenList where libelleKitchen is null
        defaultKitchenShouldNotBeFound("libelleKitchen.specified=false");
    }
                @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenContainsSomething() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen contains DEFAULT_LIBELLE_KITCHEN
        defaultKitchenShouldBeFound("libelleKitchen.contains=" + DEFAULT_LIBELLE_KITCHEN);

        // Get all the kitchenList where libelleKitchen contains UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldNotBeFound("libelleKitchen.contains=" + UPDATED_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void getAllKitchensByLibelleKitchenNotContainsSomething() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        // Get all the kitchenList where libelleKitchen does not contain DEFAULT_LIBELLE_KITCHEN
        defaultKitchenShouldNotBeFound("libelleKitchen.doesNotContain=" + DEFAULT_LIBELLE_KITCHEN);

        // Get all the kitchenList where libelleKitchen does not contain UPDATED_LIBELLE_KITCHEN
        defaultKitchenShouldBeFound("libelleKitchen.doesNotContain=" + UPDATED_LIBELLE_KITCHEN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKitchenShouldBeFound(String filter) throws Exception {
        restKitchenMockMvc.perform(get("/api/kitchens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kitchen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleKitchen").value(hasItem(DEFAULT_LIBELLE_KITCHEN)));

        // Check, that the count call also returns 1
        restKitchenMockMvc.perform(get("/api/kitchens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKitchenShouldNotBeFound(String filter) throws Exception {
        restKitchenMockMvc.perform(get("/api/kitchens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKitchenMockMvc.perform(get("/api/kitchens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingKitchen() throws Exception {
        // Get the kitchen
        restKitchenMockMvc.perform(get("/api/kitchens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        int databaseSizeBeforeUpdate = kitchenRepository.findAll().size();

        // Update the kitchen
        Kitchen updatedKitchen = kitchenRepository.findById(kitchen.getId()).get();
        // Disconnect from session so that the updates on updatedKitchen are not directly saved in db
        em.detach(updatedKitchen);
        updatedKitchen
            .libelleKitchen(UPDATED_LIBELLE_KITCHEN);
        KitchenDTO kitchenDTO = kitchenMapper.toDto(updatedKitchen);

        restKitchenMockMvc.perform(put("/api/kitchens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isOk());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeUpdate);
        Kitchen testKitchen = kitchenList.get(kitchenList.size() - 1);
        assertThat(testKitchen.getLibelleKitchen()).isEqualTo(UPDATED_LIBELLE_KITCHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingKitchen() throws Exception {
        int databaseSizeBeforeUpdate = kitchenRepository.findAll().size();

        // Create the Kitchen
        KitchenDTO kitchenDTO = kitchenMapper.toDto(kitchen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKitchenMockMvc.perform(put("/api/kitchens")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kitchenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kitchen in the database
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKitchen() throws Exception {
        // Initialize the database
        kitchenRepository.saveAndFlush(kitchen);

        int databaseSizeBeforeDelete = kitchenRepository.findAll().size();

        // Delete the kitchen
        restKitchenMockMvc.perform(delete("/api/kitchens/{id}", kitchen.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kitchen> kitchenList = kitchenRepository.findAll();
        assertThat(kitchenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
