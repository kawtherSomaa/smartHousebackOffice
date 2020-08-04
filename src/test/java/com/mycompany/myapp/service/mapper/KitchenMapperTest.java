package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KitchenMapperTest {

    private KitchenMapper kitchenMapper;

    @BeforeEach
    public void setUp() {
        kitchenMapper = new KitchenMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(kitchenMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(kitchenMapper.fromId(null)).isNull();
    }
}
