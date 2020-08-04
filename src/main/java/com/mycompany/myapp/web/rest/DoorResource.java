package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.DoorService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DoorDTO;
import com.mycompany.myapp.service.dto.DoorCriteria;
import com.mycompany.myapp.service.DoorQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Door}.
 */
@RestController
@RequestMapping("/api")
public class DoorResource {

    private final Logger log = LoggerFactory.getLogger(DoorResource.class);

    private static final String ENTITY_NAME = "door";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoorService doorService;

    private final DoorQueryService doorQueryService;

    public DoorResource(DoorService doorService, DoorQueryService doorQueryService) {
        this.doorService = doorService;
        this.doorQueryService = doorQueryService;
    }

    /**
     * {@code POST  /doors} : Create a new door.
     *
     * @param doorDTO the doorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doorDTO, or with status {@code 400 (Bad Request)} if the door has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doors")
    public ResponseEntity<DoorDTO> createDoor(@RequestBody DoorDTO doorDTO) throws URISyntaxException {
        log.debug("REST request to save Door : {}", doorDTO);
        if (doorDTO.getId() != null) {
            throw new BadRequestAlertException("A new door cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoorDTO result = doorService.save(doorDTO);
        return ResponseEntity.created(new URI("/api/doors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doors} : Updates an existing door.
     *
     * @param doorDTO the doorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doorDTO,
     * or with status {@code 400 (Bad Request)} if the doorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doors")
    public ResponseEntity<DoorDTO> updateDoor(@RequestBody DoorDTO doorDTO) throws URISyntaxException {
        log.debug("REST request to update Door : {}", doorDTO);
        if (doorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoorDTO result = doorService.save(doorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doors} : get all the doors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doors in body.
     */
    @GetMapping("/doors")
    public ResponseEntity<List<DoorDTO>> getAllDoors(DoorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Doors by criteria: {}", criteria);
        Page<DoorDTO> page = doorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doors/count} : count all the doors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/doors/count")
    public ResponseEntity<Long> countDoors(DoorCriteria criteria) {
        log.debug("REST request to count Doors by criteria: {}", criteria);
        return ResponseEntity.ok().body(doorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /doors/:id} : get the "id" door.
     *
     * @param id the id of the doorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doors/{id}")
    public ResponseEntity<DoorDTO> getDoor(@PathVariable Long id) {
        log.debug("REST request to get Door : {}", id);
        Optional<DoorDTO> doorDTO = doorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doorDTO);
    }

    /**
     * {@code DELETE  /doors/:id} : delete the "id" door.
     *
     * @param id the id of the doorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doors/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id) {
        log.debug("REST request to delete Door : {}", id);
        doorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
