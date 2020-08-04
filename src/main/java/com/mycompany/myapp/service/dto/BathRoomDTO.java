package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BathRoom} entity.
 */
@ApiModel(description = "not an ignored comment")
public class BathRoomDTO implements Serializable {
    
    private Long id;

    private String libelleBathRoom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleBathRoom() {
        return libelleBathRoom;
    }

    public void setLibelleBathRoom(String libelleBathRoom) {
        this.libelleBathRoom = libelleBathRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BathRoomDTO)) {
            return false;
        }

        return id != null && id.equals(((BathRoomDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BathRoomDTO{" +
            "id=" + getId() +
            ", libelleBathRoom='" + getLibelleBathRoom() + "'" +
            "}";
    }
}
