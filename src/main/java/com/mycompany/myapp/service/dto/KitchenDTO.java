package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Kitchen} entity.
 */
public class KitchenDTO implements Serializable {
    
    private Long id;

    private String libelleKitchen;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleKitchen() {
        return libelleKitchen;
    }

    public void setLibelleKitchen(String libelleKitchen) {
        this.libelleKitchen = libelleKitchen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KitchenDTO)) {
            return false;
        }

        return id != null && id.equals(((KitchenDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KitchenDTO{" +
            "id=" + getId() +
            ", libelleKitchen='" + getLibelleKitchen() + "'" +
            "}";
    }
}
