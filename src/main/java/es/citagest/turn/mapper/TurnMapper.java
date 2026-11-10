package es.citagest.turn.mapper;

import es.citagest.catalog.state.dto.StateDTO;
import es.citagest.catalog.state.mapper.StateMapper;
import es.citagest.dates.mapper.DateMapper;
import es.citagest.turn.dto.TurnDTO;
import es.citagest.turn.entity.TurnEntity;

public class TurnMapper {
    public static TurnDTO toDTO(TurnEntity entity){
        if (entity == null) return null;
        return new TurnDTO(entity.getId(), DateMapper.toDTO(entity.getDate()), entity.getDateUpdate(), StateMapper.toDTO(entity.getState()));
    }

    public static TurnEntity toEntity(TurnDTO dto){
        if (dto == null) return null;
        return new TurnEntity(dto.getId(), DateMapper.toEntity(dto.getDate()), dto.getDateUpdate(), StateMapper.toEntity(dto.getState()));
    }
}
