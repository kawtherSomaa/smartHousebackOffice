package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LivingRoomDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivingRoomDTO.class);
        LivingRoomDTO livingRoomDTO1 = new LivingRoomDTO();
        livingRoomDTO1.setId(1L);
        LivingRoomDTO livingRoomDTO2 = new LivingRoomDTO();
        assertThat(livingRoomDTO1).isNotEqualTo(livingRoomDTO2);
        livingRoomDTO2.setId(livingRoomDTO1.getId());
        assertThat(livingRoomDTO1).isEqualTo(livingRoomDTO2);
        livingRoomDTO2.setId(2L);
        assertThat(livingRoomDTO1).isNotEqualTo(livingRoomDTO2);
        livingRoomDTO1.setId(null);
        assertThat(livingRoomDTO1).isNotEqualTo(livingRoomDTO2);
    }
}
