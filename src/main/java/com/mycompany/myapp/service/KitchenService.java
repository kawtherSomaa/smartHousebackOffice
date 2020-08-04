package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.KitchenDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Kitchen}.
 */
public interface KitchenService {

    /**
     * Save a kitchen.
     *
     * @param kitchenDTO the entity to save.
     * @return the persisted entity.
     */
    KitchenDTO save(KitchenDTO kitchenDTO);

    /**
     * Get all the kitchens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<KitchenDTO> findAll(Pageable pageable);


    /**
     * Get the "id" kitchen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<KitchenDTO> findOne(Long id);

    /**
     * Delete the "id" kitchen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
