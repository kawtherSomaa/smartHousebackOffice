package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BathRoomMapperTest {

    private BathRoomMapper bathRoomMapper;

    @BeforeEach
    public void setUp() {
        bathRoomMapper = new BathRoomMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bathRoomMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bathRoomMapper.fromId(null)).isNull();
    }
}
