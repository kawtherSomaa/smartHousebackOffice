package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ReservationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Reservation}.
 */
public interface ReservationService {

    /**
     * Save a reservation.
     *
     * @param reservationDTO the entity to save.
     * @return the persisted entity.
     */
    ReservationDTO save(ReservationDTO reservationDTO);

    /**
     * Get all the reservations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReservationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reservation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReservationDTO> findOne(Long id);

    /**
     * Delete the "id" reservation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
