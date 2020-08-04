package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.KitchenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Kitchen} and its DTO {@link KitchenDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KitchenMapper extends EntityMapper<KitchenDTO, Kitchen> {



    default Kitchen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kitchen kitchen = new Kitchen();
        kitchen.setId(id);
        return kitchen;
    }
}
