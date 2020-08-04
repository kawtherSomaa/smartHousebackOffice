package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.House} entity.
 */
@ApiModel(description = "The House entity.")
public class HouseDTO implements Serializable {
    
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    private String libelleHouse;


    private Long userId;

    private String userLogin;

    private Long livingroomsId;

    private Long doorsId;

    private Long bathroomsId;

    private Long kitchensId;

    private Long roomsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleHouse() {
        return libelleHouse;
    }

    public void setLibelleHouse(String libelleHouse) {
        this.libelleHouse = libelleHouse;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getLivingroomsId() {
        return livingroomsId;
    }

    public void setLivingroomsId(Long livingRoomId) {
        this.livingroomsId = livingRoomId;
    }

    public Long getDoorsId() {
        return doorsId;
    }

    public void setDoorsId(Long doorId) {
        this.doorsId = doorId;
    }

    public Long getBathroomsId() {
        return bathroomsId;
    }

    public void setBathroomsId(Long bathRoomId) {
        this.bathroomsId = bathRoomId;
    }

    public Long getKitchensId() {
        return kitchensId;
    }

    public void setKitchensId(Long kitchenId) {
        this.kitchensId = kitchenId;
    }

    public Long getRoomsId() {
        return roomsId;
    }

    public void setRoomsId(Long roomId) {
        this.roomsId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HouseDTO)) {
            return false;
        }

        return id != null && id.equals(((HouseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HouseDTO{" +
            "id=" + getId() +
            ", libelleHouse='" + getLibelleHouse() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", livingroomsId=" + getLivingroomsId() +
            ", doorsId=" + getDoorsId() +
            ", bathroomsId=" + getBathroomsId() +
            ", kitchensId=" + getKitchensId() +
            ", roomsId=" + getRoomsId() +
            "}";
    }
}
