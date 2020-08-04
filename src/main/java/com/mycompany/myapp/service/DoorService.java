package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DoorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Door}.
 */
public interface DoorService {

    /**
     * Save a door.
     *
     * @param doorDTO the entity to save.
     * @return the persisted entity.
     */
    DoorDTO save(DoorDTO doorDTO);

    /**
     * Get all the doors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" door.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoorDTO> findOne(Long id);

    /**
     * Delete the "id" door.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
