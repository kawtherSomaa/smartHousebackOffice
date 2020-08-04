package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.KitchenService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.KitchenDTO;
import com.mycompany.myapp.service.dto.KitchenCriteria;
import com.mycompany.myapp.service.KitchenQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Kitchen}.
 */
@RestController
@RequestMapping("/api")
public class KitchenResource {

    private final Logger log = LoggerFactory.getLogger(KitchenResource.class);

    private static final String ENTITY_NAME = "kitchen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KitchenService kitchenService;

    private final KitchenQueryService kitchenQueryService;

    public KitchenResource(KitchenService kitchenService, KitchenQueryService kitchenQueryService) {
        this.kitchenService = kitchenService;
        this.kitchenQueryService = kitchenQueryService;
    }

    /**
     * {@code POST  /kitchens} : Create a new kitchen.
     *
     * @param kitchenDTO the kitchenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kitchenDTO, or with status {@code 400 (Bad Request)} if the kitchen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kitchens")
    public ResponseEntity<KitchenDTO> createKitchen(@RequestBody KitchenDTO kitchenDTO) throws URISyntaxException {
        log.debug("REST request to save Kitchen : {}", kitchenDTO);
        if (kitchenDTO.getId() != null) {
            throw new BadRequestAlertException("A new kitchen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KitchenDTO result = kitchenService.save(kitchenDTO);
        return ResponseEntity.created(new URI("/api/kitchens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kitchens} : Updates an existing kitchen.
     *
     * @param kitchenDTO the kitchenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kitchenDTO,
     * or with status {@code 400 (Bad Request)} if the kitchenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kitchenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kitchens")
    public ResponseEntity<KitchenDTO> updateKitchen(@RequestBody KitchenDTO kitchenDTO) throws URISyntaxException {
        log.debug("REST request to update Kitchen : {}", kitchenDTO);
        if (kitchenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KitchenDTO result = kitchenService.save(kitchenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, kitchenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kitchens} : get all the kitchens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kitchens in body.
     */
    @GetMapping("/kitchens")
    public ResponseEntity<List<KitchenDTO>> getAllKitchens(KitchenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Kitchens by criteria: {}", criteria);
        Page<KitchenDTO> page = kitchenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /kitchens/count} : count all the kitchens.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/kitchens/count")
    public ResponseEntity<Long> countKitchens(KitchenCriteria criteria) {
        log.debug("REST request to count Kitchens by criteria: {}", criteria);
        return ResponseEntity.ok().body(kitchenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /kitchens/:id} : get the "id" kitchen.
     *
     * @param id the id of the kitchenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kitchenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kitchens/{id}")
    public ResponseEntity<KitchenDTO> getKitchen(@PathVariable Long id) {
        log.debug("REST request to get Kitchen : {}", id);
        Optional<KitchenDTO> kitchenDTO = kitchenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kitchenDTO);
    }

    /**
     * {@code DELETE  /kitchens/:id} : delete the "id" kitchen.
     *
     * @param id the id of the kitchenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kitchens/{id}")
    public ResponseEntity<Void> deleteKitchen(@PathVariable Long id) {
        log.debug("REST request to delete Kitchen : {}", id);
        kitchenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
