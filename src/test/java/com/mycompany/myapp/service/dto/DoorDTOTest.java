package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DoorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DoorDTO.class);
        DoorDTO doorDTO1 = new DoorDTO();
        doorDTO1.setId(1L);
        DoorDTO doorDTO2 = new DoorDTO();
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
        doorDTO2.setId(doorDTO1.getId());
        assertThat(doorDTO1).isEqualTo(doorDTO2);
        doorDTO2.setId(2L);
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
        doorDTO1.setId(null);
        assertThat(doorDTO1).isNotEqualTo(doorDTO2);
    }
}
