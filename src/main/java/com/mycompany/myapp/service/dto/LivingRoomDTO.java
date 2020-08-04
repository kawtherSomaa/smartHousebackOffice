package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.LivingRoom} entity.
 */
public class LivingRoomDTO implements Serializable {
    
    private Long id;

    private String libelleLivingRoom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleLivingRoom() {
        return libelleLivingRoom;
    }

    public void setLibelleLivingRoom(String libelleLivingRoom) {
        this.libelleLivingRoom = libelleLivingRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivingRoomDTO)) {
            return false;
        }

        return id != null && id.equals(((LivingRoomDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivingRoomDTO{" +
            "id=" + getId() +
            ", libelleLivingRoom='" + getLibelleLivingRoom() + "'" +
            "}";
    }
}
