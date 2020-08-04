package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BathRoomDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BathRoomDTO.class);
        BathRoomDTO bathRoomDTO1 = new BathRoomDTO();
        bathRoomDTO1.setId(1L);
        BathRoomDTO bathRoomDTO2 = new BathRoomDTO();
        assertThat(bathRoomDTO1).isNotEqualTo(bathRoomDTO2);
        bathRoomDTO2.setId(bathRoomDTO1.getId());
        assertThat(bathRoomDTO1).isEqualTo(bathRoomDTO2);
        bathRoomDTO2.setId(2L);
        assertThat(bathRoomDTO1).isNotEqualTo(bathRoomDTO2);
        bathRoomDTO1.setId(null);
        assertThat(bathRoomDTO1).isNotEqualTo(bathRoomDTO2);
    }
}
