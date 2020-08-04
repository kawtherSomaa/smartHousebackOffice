package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.HouseService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.HouseDTO;
import com.mycompany.myapp.service.dto.HouseCriteria;
import com.mycompany.myapp.service.HouseQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.House}.
 */
@RestController
@RequestMapping("/api")
public class HouseResource {

    private final Logger log = LoggerFactory.getLogger(HouseResource.class);

    private static final String ENTITY_NAME = "house";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HouseService houseService;

    private final HouseQueryService houseQueryService;

    public HouseResource(HouseService houseService, HouseQueryService houseQueryService) {
        this.houseService = houseService;
        this.houseQueryService = houseQueryService;
    }

    /**
     * {@code POST  /houses} : Create a new house.
     *
     * @param houseDTO the houseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new houseDTO, or with status {@code 400 (Bad Request)} if the house has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/houses")
    public ResponseEntity<HouseDTO> createHouse(@RequestBody HouseDTO houseDTO) throws URISyntaxException {
        log.debug("REST request to save House : {}", houseDTO);
        if (houseDTO.getId() != null) {
            throw new BadRequestAlertException("A new house cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HouseDTO result = houseService.save(houseDTO);
        return ResponseEntity.created(new URI("/api/houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /houses} : Updates an existing house.
     *
     * @param houseDTO the houseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated houseDTO,
     * or with status {@code 400 (Bad Request)} if the houseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the houseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/houses")
    public ResponseEntity<HouseDTO> updateHouse(@RequestBody HouseDTO houseDTO) throws URISyntaxException {
        log.debug("REST request to update House : {}", houseDTO);
        if (houseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HouseDTO result = houseService.save(houseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, houseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /houses} : get all the houses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of houses in body.
     */
    @GetMapping("/houses")
    public ResponseEntity<List<HouseDTO>> getAllHouses(HouseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Houses by criteria: {}", criteria);
        Page<HouseDTO> page = houseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /houses/count} : count all the houses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/houses/count")
    public ResponseEntity<Long> countHouses(HouseCriteria criteria) {
        log.debug("REST request to count Houses by criteria: {}", criteria);
        return ResponseEntity.ok().body(houseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /houses/:id} : get the "id" house.
     *
     * @param id the id of the houseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the houseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/houses/{id}")
    public ResponseEntity<HouseDTO> getHouse(@PathVariable Long id) {
        log.debug("REST request to get House : {}", id);
        Optional<HouseDTO> houseDTO = houseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(houseDTO);
    }

    /**
     * {@code DELETE  /houses/:id} : delete the "id" house.
     *
     * @param id the id of the houseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/houses/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        log.debug("REST request to delete House : {}", id);
        houseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
