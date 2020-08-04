package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DoorService;
import com.mycompany.myapp.domain.Door;
import com.mycompany.myapp.repository.DoorRepository;
import com.mycompany.myapp.service.dto.DoorDTO;
import com.mycompany.myapp.service.mapper.DoorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Door}.
 */
@Service
@Transactional
public class DoorServiceImpl implements DoorService {

    private final Logger log = LoggerFactory.getLogger(DoorServiceImpl.class);

    private final DoorRepository doorRepository;

    private final DoorMapper doorMapper;

    public DoorServiceImpl(DoorRepository doorRepository, DoorMapper doorMapper) {
        this.doorRepository = doorRepository;
        this.doorMapper = doorMapper;
    }

    @Override
    public DoorDTO save(DoorDTO doorDTO) {
        log.debug("Request to save Door : {}", doorDTO);
        Door door = doorMapper.toEntity(doorDTO);
        door = doorRepository.save(door);
        return doorMapper.toDto(door);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Doors");
        return doorRepository.findAll(pageable)
            .map(doorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DoorDTO> findOne(Long id) {
        log.debug("Request to get Door : {}", id);
        return doorRepository.findById(id)
            .map(doorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Door : {}", id);
        doorRepository.deleteById(id);
    }
}
