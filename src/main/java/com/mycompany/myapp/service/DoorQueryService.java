package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Door;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.DoorRepository;
import com.mycompany.myapp.service.dto.DoorCriteria;
import com.mycompany.myapp.service.dto.DoorDTO;
import com.mycompany.myapp.service.mapper.DoorMapper;

/**
 * Service for executing complex queries for {@link Door} entities in the database.
 * The main input is a {@link DoorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DoorDTO} or a {@link Page} of {@link DoorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DoorQueryService extends QueryService<Door> {

    private final Logger log = LoggerFactory.getLogger(DoorQueryService.class);

    private final DoorRepository doorRepository;

    private final DoorMapper doorMapper;

    public DoorQueryService(DoorRepository doorRepository, DoorMapper doorMapper) {
        this.doorRepository = doorRepository;
        this.doorMapper = doorMapper;
    }

    /**
     * Return a {@link List} of {@link DoorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DoorDTO> findByCriteria(DoorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Door> specification = createSpecification(criteria);
        return doorMapper.toDto(doorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DoorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DoorDTO> findByCriteria(DoorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Door> specification = createSpecification(criteria);
        return doorRepository.findAll(specification, page)
            .map(doorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DoorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Door> specification = createSpecification(criteria);
        return doorRepository.count(specification);
    }

    /**
     * Function to convert {@link DoorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Door> createSpecification(DoorCriteria criteria) {
        Specification<Door> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Door_.id));
            }
            if (criteria.getLibelleDoor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleDoor(), Door_.libelleDoor));
            }
        }
        return specification;
    }
}
