package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BathRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BathRoom} and its DTO {@link BathRoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BathRoomMapper extends EntityMapper<BathRoomDTO, BathRoom> {



    default BathRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        BathRoom bathRoom = new BathRoom();
        bathRoom.setId(id);
        return bathRoom;
    }
}
