package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class HouseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HouseDTO.class);
        HouseDTO houseDTO1 = new HouseDTO();
        houseDTO1.setId(1L);
        HouseDTO houseDTO2 = new HouseDTO();
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
        houseDTO2.setId(houseDTO1.getId());
        assertThat(houseDTO1).isEqualTo(houseDTO2);
        houseDTO2.setId(2L);
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
        houseDTO1.setId(null);
        assertThat(houseDTO1).isNotEqualTo(houseDTO2);
    }
}
