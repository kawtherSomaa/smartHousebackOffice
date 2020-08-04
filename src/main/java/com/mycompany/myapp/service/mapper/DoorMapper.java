package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DoorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Door} and its DTO {@link DoorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DoorMapper extends EntityMapper<DoorDTO, Door> {



    default Door fromId(Long id) {
        if (id == null) {
            return null;
        }
        Door door = new Door();
        door.setId(id);
        return door;
    }
}
