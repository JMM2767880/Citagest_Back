package es.citagest.dates.mapper;

import es.citagest.catalog.state.mapper.StateMapper;
import es.citagest.dates.dto.DateDTO;
import es.citagest.dates.entity.DateEntity;
import es.citagest.user.mapper.UserMapper;


public class DateMapper {
    public static DateDTO toDTO(DateEntity entity){
        if (entity == null) return null;
        return new DateDTO(entity.getId(), entity.getNameClient(), entity.getNameClient(), entity.getDateTime(), StateMapper.toDTO(entity.getState()), UserMapper.toDTO(entity.getUser()), entity.getDateCode());
    }

    public static DateEntity toEntity(DateDTO dto){
        if (dto == null) return null;
        return new DateEntity(dto.getId(), dto.getNameClient(), dto.getNameClient(), dto.getDateTime(), StateMapper.toEntity(dto.getState()), UserMapper.toEntity(dto.getUser()), dto.getDateCode());
    }
}
