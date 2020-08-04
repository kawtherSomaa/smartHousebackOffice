package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LivingRoomTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivingRoom.class);
        LivingRoom livingRoom1 = new LivingRoom();
        livingRoom1.setId(1L);
        LivingRoom livingRoom2 = new LivingRoom();
        livingRoom2.setId(livingRoom1.getId());
        assertThat(livingRoom1).isEqualTo(livingRoom2);
        livingRoom2.setId(2L);
        assertThat(livingRoom1).isNotEqualTo(livingRoom2);
        livingRoom1.setId(null);
        assertThat(livingRoom1).isNotEqualTo(livingRoom2);
    }
}
