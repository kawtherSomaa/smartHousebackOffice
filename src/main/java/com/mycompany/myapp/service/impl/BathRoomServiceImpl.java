package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BathRoomService;
import com.mycompany.myapp.domain.BathRoom;
import com.mycompany.myapp.repository.BathRoomRepository;
import com.mycompany.myapp.service.dto.BathRoomDTO;
import com.mycompany.myapp.service.mapper.BathRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BathRoom}.
 */
@Service
@Transactional
public class BathRoomServiceImpl implements BathRoomService {

    private final Logger log = LoggerFactory.getLogger(BathRoomServiceImpl.class);

    private final BathRoomRepository bathRoomRepository;

    private final BathRoomMapper bathRoomMapper;

    public BathRoomServiceImpl(BathRoomRepository bathRoomRepository, BathRoomMapper bathRoomMapper) {
        this.bathRoomRepository = bathRoomRepository;
        this.bathRoomMapper = bathRoomMapper;
    }

    @Override
    public BathRoomDTO save(BathRoomDTO bathRoomDTO) {
        log.debug("Request to save BathRoom : {}", bathRoomDTO);
        BathRoom bathRoom = bathRoomMapper.toEntity(bathRoomDTO);
        bathRoom = bathRoomRepository.save(bathRoom);
        return bathRoomMapper.toDto(bathRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BathRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BathRooms");
        return bathRoomRepository.findAll(pageable)
            .map(bathRoomMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BathRoomDTO> findOne(Long id) {
        log.debug("Request to get BathRoom : {}", id);
        return bathRoomRepository.findById(id)
            .map(bathRoomMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BathRoom : {}", id);
        bathRoomRepository.deleteById(id);
    }
}
