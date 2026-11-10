package es.citagest.template.mapper;

import es.citagest.template.dto.TemplateDateDTO;
import es.citagest.template.entity.TemplateDateEntity;
import es.citagest.user.mapper.UserMapper;

public class TemplateDateMapper {
    public static TemplateDateDTO toDTO(TemplateDateEntity entity){
        if (entity == null) return null;
        return new TemplateDateDTO(entity.getId(), entity.getWeekday(), entity.getHour(), UserMapper.toDTO(entity.getUser()), entity.getNumberTemplate());
    }

    public static TemplateDateEntity toEntity(TemplateDateDTO dto){
        if (dto == null) return null;
        return new TemplateDateEntity(dto.getWeekday(), dto.getHour(), UserMapper.toEntity(dto.getUser()), dto.getNumberTemplate());
    }
}

