package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Room} entity.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
public class RoomDTO implements Serializable {
    
    private Long id;

    private String libelleRoom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleRoom() {
        return libelleRoom;
    }

    public void setLibelleRoom(String libelleRoom) {
        this.libelleRoom = libelleRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomDTO)) {
            return false;
        }

        return id != null && id.equals(((RoomDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomDTO{" +
            "id=" + getId() +
            ", libelleRoom='" + getLibelleRoom() + "'" +
            "}";
    }
}
