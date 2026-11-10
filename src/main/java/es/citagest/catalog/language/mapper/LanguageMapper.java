package es.citagest.catalog.language.mapper;

import es.citagest.catalog.language.dto.LanguageDTO;
import es.citagest.catalog.language.entity.LanguageEntity;

public class LanguageMapper {
    public static LanguageDTO toDTO(LanguageEntity entity){
        if (entity == null) return null;
        return new LanguageDTO(entity.getCode(), entity.getName());
    }

    public static LanguageEntity toEntity(LanguageDTO dto){
        if (dto == null) return null;
        return new LanguageEntity(dto.getCode(), dto.getName());
    }
}
