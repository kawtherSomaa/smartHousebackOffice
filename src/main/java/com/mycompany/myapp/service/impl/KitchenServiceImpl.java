package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.KitchenService;
import com.mycompany.myapp.domain.Kitchen;
import com.mycompany.myapp.repository.KitchenRepository;
import com.mycompany.myapp.service.dto.KitchenDTO;
import com.mycompany.myapp.service.mapper.KitchenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Kitchen}.
 */
@Service
@Transactional
public class KitchenServiceImpl implements KitchenService {

    private final Logger log = LoggerFactory.getLogger(KitchenServiceImpl.class);

    private final KitchenRepository kitchenRepository;

    private final KitchenMapper kitchenMapper;

    public KitchenServiceImpl(KitchenRepository kitchenRepository, KitchenMapper kitchenMapper) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenMapper = kitchenMapper;
    }

    @Override
    public KitchenDTO save(KitchenDTO kitchenDTO) {
        log.debug("Request to save Kitchen : {}", kitchenDTO);
        Kitchen kitchen = kitchenMapper.toEntity(kitchenDTO);
        kitchen = kitchenRepository.save(kitchen);
        return kitchenMapper.toDto(kitchen);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KitchenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Kitchens");
        return kitchenRepository.findAll(pageable)
            .map(kitchenMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<KitchenDTO> findOne(Long id) {
        log.debug("Request to get Kitchen : {}", id);
        return kitchenRepository.findById(id)
            .map(kitchenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kitchen : {}", id);
        kitchenRepository.deleteById(id);
    }
}
