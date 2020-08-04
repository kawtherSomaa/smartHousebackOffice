package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.HouseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.House}.
 */
public interface HouseService {

    /**
     * Save a house.
     *
     * @param houseDTO the entity to save.
     * @return the persisted entity.
     */
    HouseDTO save(HouseDTO houseDTO);

    /**
     * Get all the houses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HouseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" house.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HouseDTO> findOne(Long id);

    /**
     * Delete the "id" house.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
