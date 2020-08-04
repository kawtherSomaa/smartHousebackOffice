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

import com.mycompany.myapp.domain.House;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.HouseRepository;
import com.mycompany.myapp.service.dto.HouseCriteria;
import com.mycompany.myapp.service.dto.HouseDTO;
import com.mycompany.myapp.service.mapper.HouseMapper;

/**
 * Service for executing complex queries for {@link House} entities in the database.
 * The main input is a {@link HouseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HouseDTO} or a {@link Page} of {@link HouseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HouseQueryService extends QueryService<House> {

    private final Logger log = LoggerFactory.getLogger(HouseQueryService.class);

    private final HouseRepository houseRepository;

    private final HouseMapper houseMapper;

    public HouseQueryService(HouseRepository houseRepository, HouseMapper houseMapper) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
    }

    /**
     * Return a {@link List} of {@link HouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HouseDTO> findByCriteria(HouseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<House> specification = createSpecification(criteria);
        return houseMapper.toDto(houseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HouseDTO> findByCriteria(HouseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<House> specification = createSpecification(criteria);
        return houseRepository.findAll(specification, page)
            .map(houseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HouseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<House> specification = createSpecification(criteria);
        return houseRepository.count(specification);
    }

    /**
     * Function to convert {@link HouseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<House> createSpecification(HouseCriteria criteria) {
        Specification<House> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), House_.id));
            }
            if (criteria.getLibelleHouse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleHouse(), House_.libelleHouse));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(House_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getLivingroomsId() != null) {
                specification = specification.and(buildSpecification(criteria.getLivingroomsId(),
                    root -> root.join(House_.livingrooms, JoinType.LEFT).get(LivingRoom_.id)));
            }
            if (criteria.getDoorsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDoorsId(),
                    root -> root.join(House_.doors, JoinType.LEFT).get(Door_.id)));
            }
            if (criteria.getBathroomsId() != null) {
                specification = specification.and(buildSpecification(criteria.getBathroomsId(),
                    root -> root.join(House_.bathrooms, JoinType.LEFT).get(BathRoom_.id)));
            }
            if (criteria.getKitchensId() != null) {
                specification = specification.and(buildSpecification(criteria.getKitchensId(),
                    root -> root.join(House_.kitchens, JoinType.LEFT).get(Kitchen_.id)));
            }
            if (criteria.getRoomsId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoomsId(),
                    root -> root.join(House_.rooms, JoinType.LEFT).get(Room_.id)));
            }
        }
        return specification;
    }
}
