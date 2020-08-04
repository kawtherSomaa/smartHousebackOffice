package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BathRoomService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.BathRoomDTO;
import com.mycompany.myapp.service.dto.BathRoomCriteria;
import com.mycompany.myapp.service.BathRoomQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.BathRoom}.
 */
@RestController
@RequestMapping("/api")
public class BathRoomResource {

    private final Logger log = LoggerFactory.getLogger(BathRoomResource.class);

    private static final String ENTITY_NAME = "bathRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BathRoomService bathRoomService;

    private final BathRoomQueryService bathRoomQueryService;

    public BathRoomResource(BathRoomService bathRoomService, BathRoomQueryService bathRoomQueryService) {
        this.bathRoomService = bathRoomService;
        this.bathRoomQueryService = bathRoomQueryService;
    }

    /**
     * {@code POST  /bath-rooms} : Create a new bathRoom.
     *
     * @param bathRoomDTO the bathRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bathRoomDTO, or with status {@code 400 (Bad Request)} if the bathRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bath-rooms")
    public ResponseEntity<BathRoomDTO> createBathRoom(@RequestBody BathRoomDTO bathRoomDTO) throws URISyntaxException {
        log.debug("REST request to save BathRoom : {}", bathRoomDTO);
        if (bathRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new bathRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BathRoomDTO result = bathRoomService.save(bathRoomDTO);
        return ResponseEntity.created(new URI("/api/bath-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bath-rooms} : Updates an existing bathRoom.
     *
     * @param bathRoomDTO the bathRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bathRoomDTO,
     * or with status {@code 400 (Bad Request)} if the bathRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bathRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bath-rooms")
    public ResponseEntity<BathRoomDTO> updateBathRoom(@RequestBody BathRoomDTO bathRoomDTO) throws URISyntaxException {
        log.debug("REST request to update BathRoom : {}", bathRoomDTO);
        if (bathRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BathRoomDTO result = bathRoomService.save(bathRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bathRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bath-rooms} : get all the bathRooms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bathRooms in body.
     */
    @GetMapping("/bath-rooms")
    public ResponseEntity<List<BathRoomDTO>> getAllBathRooms(BathRoomCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BathRooms by criteria: {}", criteria);
        Page<BathRoomDTO> page = bathRoomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bath-rooms/count} : count all the bathRooms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bath-rooms/count")
    public ResponseEntity<Long> countBathRooms(BathRoomCriteria criteria) {
        log.debug("REST request to count BathRooms by criteria: {}", criteria);
        return ResponseEntity.ok().body(bathRoomQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bath-rooms/:id} : get the "id" bathRoom.
     *
     * @param id the id of the bathRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bathRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bath-rooms/{id}")
    public ResponseEntity<BathRoomDTO> getBathRoom(@PathVariable Long id) {
        log.debug("REST request to get BathRoom : {}", id);
        Optional<BathRoomDTO> bathRoomDTO = bathRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bathRoomDTO);
    }

    /**
     * {@code DELETE  /bath-rooms/:id} : delete the "id" bathRoom.
     *
     * @param id the id of the bathRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bath-rooms/{id}")
    public ResponseEntity<Void> deleteBathRoom(@PathVariable Long id) {
        log.debug("REST request to delete BathRoom : {}", id);
        bathRoomService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
