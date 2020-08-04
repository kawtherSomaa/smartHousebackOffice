package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class KitchenDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KitchenDTO.class);
        KitchenDTO kitchenDTO1 = new KitchenDTO();
        kitchenDTO1.setId(1L);
        KitchenDTO kitchenDTO2 = new KitchenDTO();
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
        kitchenDTO2.setId(kitchenDTO1.getId());
        assertThat(kitchenDTO1).isEqualTo(kitchenDTO2);
        kitchenDTO2.setId(2L);
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
        kitchenDTO1.setId(null);
        assertThat(kitchenDTO1).isNotEqualTo(kitchenDTO2);
    }
}
