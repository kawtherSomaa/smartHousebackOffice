package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LivingRoomMapperTest {

    private LivingRoomMapper livingRoomMapper;

    @BeforeEach
    public void setUp() {
        livingRoomMapper = new LivingRoomMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(livingRoomMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(livingRoomMapper.fromId(null)).isNull();
    }
}
