package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.LivingRoomService;
import com.mycompany.myapp.domain.LivingRoom;
import com.mycompany.myapp.repository.LivingRoomRepository;
import com.mycompany.myapp.service.dto.LivingRoomDTO;
import com.mycompany.myapp.service.mapper.LivingRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LivingRoom}.
 */
@Service
@Transactional
public class LivingRoomServiceImpl implements LivingRoomService {

    private final Logger log = LoggerFactory.getLogger(LivingRoomServiceImpl.class);

    private final LivingRoomRepository livingRoomRepository;

    private final LivingRoomMapper livingRoomMapper;

    public LivingRoomServiceImpl(LivingRoomRepository livingRoomRepository, LivingRoomMapper livingRoomMapper) {
        this.livingRoomRepository = livingRoomRepository;
        this.livingRoomMapper = livingRoomMapper;
    }

    @Override
    public LivingRoomDTO save(LivingRoomDTO livingRoomDTO) {
        log.debug("Request to save LivingRoom : {}", livingRoomDTO);
        LivingRoom livingRoom = livingRoomMapper.toEntity(livingRoomDTO);
        livingRoom = livingRoomRepository.save(livingRoom);
        return livingRoomMapper.toDto(livingRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LivingRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LivingRooms");
        return livingRoomRepository.findAll(pageable)
            .map(livingRoomMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LivingRoomDTO> findOne(Long id) {
        log.debug("Request to get LivingRoom : {}", id);
        return livingRoomRepository.findById(id)
            .map(livingRoomMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LivingRoom : {}", id);
        livingRoomRepository.deleteById(id);
    }
}
