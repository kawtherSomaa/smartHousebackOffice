package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.LivingRoomService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.LivingRoomDTO;
import com.mycompany.myapp.service.dto.LivingRoomCriteria;
import com.mycompany.myapp.service.LivingRoomQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.LivingRoom}.
 */
@RestController
@RequestMapping("/api")
public class LivingRoomResource {

    private final Logger log = LoggerFactory.getLogger(LivingRoomResource.class);

    private static final String ENTITY_NAME = "livingRoom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivingRoomService livingRoomService;

    private final LivingRoomQueryService livingRoomQueryService;

    public LivingRoomResource(LivingRoomService livingRoomService, LivingRoomQueryService livingRoomQueryService) {
        this.livingRoomService = livingRoomService;
        this.livingRoomQueryService = livingRoomQueryService;
    }

    /**
     * {@code POST  /living-rooms} : Create a new livingRoom.
     *
     * @param livingRoomDTO the livingRoomDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livingRoomDTO, or with status {@code 400 (Bad Request)} if the livingRoom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/living-rooms")
    public ResponseEntity<LivingRoomDTO> createLivingRoom(@RequestBody LivingRoomDTO livingRoomDTO) throws URISyntaxException {
        log.debug("REST request to save LivingRoom : {}", livingRoomDTO);
        if (livingRoomDTO.getId() != null) {
            throw new BadRequestAlertException("A new livingRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivingRoomDTO result = livingRoomService.save(livingRoomDTO);
        return ResponseEntity.created(new URI("/api/living-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /living-rooms} : Updates an existing livingRoom.
     *
     * @param livingRoomDTO the livingRoomDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livingRoomDTO,
     * or with status {@code 400 (Bad Request)} if the livingRoomDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livingRoomDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/living-rooms")
    public ResponseEntity<LivingRoomDTO> updateLivingRoom(@RequestBody LivingRoomDTO livingRoomDTO) throws URISyntaxException {
        log.debug("REST request to update LivingRoom : {}", livingRoomDTO);
        if (livingRoomDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LivingRoomDTO result = livingRoomService.save(livingRoomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, livingRoomDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /living-rooms} : get all the livingRooms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livingRooms in body.
     */
    @GetMapping("/living-rooms")
    public ResponseEntity<List<LivingRoomDTO>> getAllLivingRooms(LivingRoomCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LivingRooms by criteria: {}", criteria);
        Page<LivingRoomDTO> page = livingRoomQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /living-rooms/count} : count all the livingRooms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/living-rooms/count")
    public ResponseEntity<Long> countLivingRooms(LivingRoomCriteria criteria) {
        log.debug("REST request to count LivingRooms by criteria: {}", criteria);
        return ResponseEntity.ok().body(livingRoomQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /living-rooms/:id} : get the "id" livingRoom.
     *
     * @param id the id of the livingRoomDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livingRoomDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/living-rooms/{id}")
    public ResponseEntity<LivingRoomDTO> getLivingRoom(@PathVariable Long id) {
        log.debug("REST request to get LivingRoom : {}", id);
        Optional<LivingRoomDTO> livingRoomDTO = livingRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(livingRoomDTO);
    }

    /**
     * {@code DELETE  /living-rooms/:id} : delete the "id" livingRoom.
     *
     * @param id the id of the livingRoomDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/living-rooms/{id}")
    public ResponseEntity<Void> deleteLivingRoom(@PathVariable Long id) {
        log.debug("REST request to delete LivingRoom : {}", id);
        livingRoomService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
