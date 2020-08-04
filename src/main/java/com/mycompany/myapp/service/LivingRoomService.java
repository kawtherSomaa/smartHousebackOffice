package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LivingRoomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.LivingRoom}.
 */
public interface LivingRoomService {

    /**
     * Save a livingRoom.
     *
     * @param livingRoomDTO the entity to save.
     * @return the persisted entity.
     */
    LivingRoomDTO save(LivingRoomDTO livingRoomDTO);

    /**
     * Get all the livingRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LivingRoomDTO> findAll(Pageable pageable);


    /**
     * Get the "id" livingRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LivingRoomDTO> findOne(Long id);

    /**
     * Delete the "id" livingRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
