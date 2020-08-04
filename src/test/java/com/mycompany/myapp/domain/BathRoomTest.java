package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BathRoomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BathRoom.class);
        BathRoom bathRoom1 = new BathRoom();
        bathRoom1.setId(1L);
        BathRoom bathRoom2 = new BathRoom();
        bathRoom2.setId(bathRoom1.getId());
        assertThat(bathRoom1).isEqualTo(bathRoom2);
        bathRoom2.setId(2L);
        assertThat(bathRoom1).isNotEqualTo(bathRoom2);
        bathRoom1.setId(null);
        assertThat(bathRoom1).isNotEqualTo(bathRoom2);
    }
}
