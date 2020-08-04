package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KitchenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kitchen.class);
        Kitchen kitchen1 = new Kitchen();
        kitchen1.setId(1L);
        Kitchen kitchen2 = new Kitchen();
        kitchen2.setId(kitchen1.getId());
        assertThat(kitchen1).isEqualTo(kitchen2);
        kitchen2.setId(2L);
        assertThat(kitchen1).isNotEqualTo(kitchen2);
        kitchen1.setId(null);
        assertThat(kitchen1).isNotEqualTo(kitchen2);
    }
}
