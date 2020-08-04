package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Door} entity.
 */
public class DoorDTO implements Serializable {
    
    private Long id;

    private String libelleDoor;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleDoor() {
        return libelleDoor;
    }

    public void setLibelleDoor(String libelleDoor) {
        this.libelleDoor = libelleDoor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoorDTO)) {
            return false;
        }

        return id != null && id.equals(((DoorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DoorDTO{" +
            "id=" + getId() +
            ", libelleDoor='" + getLibelleDoor() + "'" +
            "}";
    }
}
