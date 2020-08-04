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

import com.mycompany.myapp.domain.LivingRoom;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.LivingRoomRepository;
import com.mycompany.myapp.service.dto.LivingRoomCriteria;
import com.mycompany.myapp.service.dto.LivingRoomDTO;
import com.mycompany.myapp.service.mapper.LivingRoomMapper;

/**
 * Service for executing complex queries for {@link LivingRoom} entities in the database.
 * The main input is a {@link LivingRoomCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LivingRoomDTO} or a {@link Page} of {@link LivingRoomDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LivingRoomQueryService extends QueryService<LivingRoom> {

    private final Logger log = LoggerFactory.getLogger(LivingRoomQueryService.class);

    private final LivingRoomRepository livingRoomRepository;

    private final LivingRoomMapper livingRoomMapper;

    public LivingRoomQueryService(LivingRoomRepository livingRoomRepository, LivingRoomMapper livingRoomMapper) {
        this.livingRoomRepository = livingRoomRepository;
        this.livingRoomMapper = livingRoomMapper;
    }

    /**
     * Return a {@link List} of {@link LivingRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LivingRoomDTO> findByCriteria(LivingRoomCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LivingRoom> specification = createSpecification(criteria);
        return livingRoomMapper.toDto(livingRoomRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LivingRoomDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LivingRoomDTO> findByCriteria(LivingRoomCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LivingRoom> specification = createSpecification(criteria);
        return livingRoomRepository.findAll(specification, page)
            .map(livingRoomMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LivingRoomCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LivingRoom> specification = createSpecification(criteria);
        return livingRoomRepository.count(specification);
    }

    /**
     * Function to convert {@link LivingRoomCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LivingRoom> createSpecification(LivingRoomCriteria criteria) {
        Specification<LivingRoom> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LivingRoom_.id));
            }
            if (criteria.getLibelleLivingRoom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelleLivingRoom(), LivingRoom_.libelleLivingRoom));
            }
        }
        return specification;
    }
}
