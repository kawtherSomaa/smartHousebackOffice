package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HouseMapperTest {

    private HouseMapper houseMapper;

    @BeforeEach
    public void setUp() {
        houseMapper = new HouseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(houseMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(houseMapper.fromId(null)).isNull();
    }
}
