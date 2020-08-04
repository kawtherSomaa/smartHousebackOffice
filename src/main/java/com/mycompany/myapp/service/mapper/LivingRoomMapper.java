package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LivingRoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LivingRoom} and its DTO {@link LivingRoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LivingRoomMapper extends EntityMapper<LivingRoomDTO, LivingRoom> {



    default LivingRoom fromId(Long id) {
        if (id == null) {
            return null;
        }
        LivingRoom livingRoom = new LivingRoom();
        livingRoom.setId(id);
        return livingRoom;
    }
}
