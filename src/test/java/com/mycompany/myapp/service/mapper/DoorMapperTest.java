package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DoorMapperTest {

    private DoorMapper doorMapper;

    @BeforeEach
    public void setUp() {
        doorMapper = new DoorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(doorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(doorMapper.fromId(null)).isNull();
    }
}
