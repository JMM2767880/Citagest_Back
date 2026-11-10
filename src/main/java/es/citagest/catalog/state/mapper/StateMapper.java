package es.citagest.catalog.state.mapper;

import es.citagest.catalog.state.dto.StateDTO;
import es.citagest.catalog.state.entity.StateEntity;

public interface StateMapper {
    public static StateDTO toDTO(StateEntity entity) {
        if (entity == null) return null;
        return new StateDTO(entity.getId(), entity.getName(), StateDTO.StateType.valueOf(entity.getType().name()), entity.getCode(), entity.getDescription());
    }

    public static StateEntity toEntity(StateDTO dto){
        if (dto == null) return null;
        return new StateEntity(dto.getName(), StateEntity.StateType.valueOf(dto.getType().name()), dto.getCode(), dto.getDescription());
    }
}
