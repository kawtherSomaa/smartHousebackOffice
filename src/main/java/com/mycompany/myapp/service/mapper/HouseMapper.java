package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.HouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link House} and its DTO {@link HouseDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LivingRoomMapper.class, DoorMapper.class, BathRoomMapper.class, KitchenMapper.class, RoomMapper.class})
public interface HouseMapper extends EntityMapper<HouseDTO, House> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "livingrooms.id", target = "livingroomsId")
    @Mapping(source = "doors.id", target = "doorsId")
    @Mapping(source = "bathrooms.id", target = "bathroomsId")
    @Mapping(source = "kitchens.id", target = "kitchensId")
    @Mapping(source = "rooms.id", target = "roomsId")
    HouseDTO toDto(House house);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "livingroomsId", target = "livingrooms")
    @Mapping(source = "doorsId", target = "doors")
    @Mapping(source = "bathroomsId", target = "bathrooms")
    @Mapping(source = "kitchensId", target = "kitchens")
    @Mapping(source = "roomsId", target = "rooms")
    House toEntity(HouseDTO houseDTO);

    default House fromId(Long id) {
        if (id == null) {
            return null;
        }
        House house = new House();
        house.setId(id);
        return house;
    }
}
