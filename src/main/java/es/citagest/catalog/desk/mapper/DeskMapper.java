package es.citagest.catalog.desk.mapper;

import es.citagest.catalog.desk.dto.DeskDTO;
import es.citagest.catalog.desk.entity.DeskEntity;

public class DeskMapper {
    public static DeskDTO toDTO(DeskEntity entity){
        if (entity == null) return null;
        return new DeskDTO(entity.getId(), entity.getName(), entity.getCode(), entity.getEnabled());
    }

    public static DeskEntity toEntity(DeskDTO dto){
        if (dto == null) return null;
        return new DeskEntity(dto.getName(), dto.getCode(), dto.getEnabled());
    }
}
