package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.HouseService;
import com.mycompany.myapp.domain.House;
import com.mycompany.myapp.repository.HouseRepository;
import com.mycompany.myapp.service.dto.HouseDTO;
import com.mycompany.myapp.service.mapper.HouseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link House}.
 */
@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    private final Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);

    private final HouseRepository houseRepository;

    private final HouseMapper houseMapper;

    public HouseServiceImpl(HouseRepository houseRepository, HouseMapper houseMapper) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
    }

    @Override
    public HouseDTO save(HouseDTO houseDTO) {
        log.debug("Request to save House : {}", houseDTO);
        House house = houseMapper.toEntity(houseDTO);
        house = houseRepository.save(house);
        return houseMapper.toDto(house);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HouseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Houses");
        return houseRepository.findAll(pageable)
            .map(houseMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<HouseDTO> findOne(Long id) {
        log.debug("Request to get House : {}", id);
        return houseRepository.findById(id)
            .map(houseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete House : {}", id);
        houseRepository.deleteById(id);
    }
}
