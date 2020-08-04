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

import com.mycompany.myapp.domain.BathRoom;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.BathRoomRepository;
import com.mycompany.myapp.service.dto.BathRoomCriteria;
import com.mycompany.myapp.service.dto.BathRoomDTO;
import com.mycompany.myapp.service.mapper.BathRoomMapper;

/**
 * Service for executing complex queries for {@link BathRoom} entities in the database.
 * The main input is a {@link BathRoomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BathRoomDTO} or a {@link Page} of {@link BathRoomDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BathRoomQueryService extends QueryService<BathRoom> {

    private final Logger log = LoggerFactory.getLogger(BathRoomQueryService.class);

    private final BathRoomRepository bathRoomRepository;

    private final BathRoomMapper bathRoomMapper;

    public BathRoomQueryService(BathRoomRepository bathRoomRepository, BathRoomMapper bathRoomMapper) {
        this.bathRoomRepository = bathRoomRepository;
        this.bathRoomMapper = bathRoomMapper;
    }

    /**
     * Return a {@link List} of {@link BathRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BathRoomDTO> findByCriteria(BathRoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BathRoom> specification = createSpecification(criteria);
        return bathRoomMapper.toDto(bathRoomRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BathRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BathRoomDTO> findByCriteria(BathRoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BathRoom> specification = createSpecification(criteria);
        return bathRoomRepository.findAll(specification, page)
            .map(bathRoomMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BathRoomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BathRoom> specification = createSpecification(criteria);
        return bathRoomRepository.count(specification);
    }

    /**
     * Function to convert {@link BathRoomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BathRoom> createSpecification(BathRoomCriteria criteria) {
        Specification<BathRoom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BathRoom_.id));
            }
            if (criteria.getLibelleBathRoom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleBathRoom(), BathRoom_.libelleBathRoom));
            }
        }
        return specification;
    }
}
