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

import com.mycompany.myapp.domain.Kitchen;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.KitchenRepository;
import com.mycompany.myapp.service.dto.KitchenCriteria;
import com.mycompany.myapp.service.dto.KitchenDTO;
import com.mycompany.myapp.service.mapper.KitchenMapper;

/**
 * Service for executing complex queries for {@link Kitchen} entities in the database.
 * The main input is a {@link KitchenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KitchenDTO} or a {@link Page} of {@link KitchenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KitchenQueryService extends QueryService<Kitchen> {

    private final Logger log = LoggerFactory.getLogger(KitchenQueryService.class);

    private final KitchenRepository kitchenRepository;

    private final KitchenMapper kitchenMapper;

    public KitchenQueryService(KitchenRepository kitchenRepository, KitchenMapper kitchenMapper) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenMapper = kitchenMapper;
    }

    /**
     * Return a {@link List} of {@link KitchenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KitchenDTO> findByCriteria(KitchenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Kitchen> specification = createSpecification(criteria);
        return kitchenMapper.toDto(kitchenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link KitchenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KitchenDTO> findByCriteria(KitchenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Kitchen> specification = createSpecification(criteria);
        return kitchenRepository.findAll(specification, page)
            .map(kitchenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KitchenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Kitchen> specification = createSpecification(criteria);
        return kitchenRepository.count(specification);
    }

    /**
     * Function to convert {@link KitchenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Kitchen> createSpecification(KitchenCriteria criteria) {
        Specification<Kitchen> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Kitchen_.id));
            }
            if (criteria.getLibelleKitchen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleKitchen(), Kitchen_.libelleKitchen));
            }
        }
        return specification;
    }
}
