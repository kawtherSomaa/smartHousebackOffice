package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class HouseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(House.class);
        House house1 = new House();
        house1.setId(1L);
        House house2 = new House();
        house2.setId(house1.getId());
        assertThat(house1).isEqualTo(house2);
        house2.setId(2L);
        assertThat(house1).isNotEqualTo(house2);
        house1.setId(null);
        assertThat(house1).isNotEqualTo(house2);
    }
}
