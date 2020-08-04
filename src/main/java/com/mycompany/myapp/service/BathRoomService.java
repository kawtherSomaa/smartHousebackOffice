package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BathRoomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.BathRoom}.
 */
public interface BathRoomService {

    /**
     * Save a bathRoom.
     *
     * @param bathRoomDTO the entity to save.
     * @return the persisted entity.
     */
    BathRoomDTO save(BathRoomDTO bathRoomDTO);

    /**
     * Get all the bathRooms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BathRoomDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bathRoom.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BathRoomDTO> findOne(Long id);

    /**
     * Delete the "id" bathRoom.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
