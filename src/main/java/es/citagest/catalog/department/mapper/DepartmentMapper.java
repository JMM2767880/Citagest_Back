package es.citagest.catalog.department.mapper;

import es.citagest.catalog.department.dto.DepartmentDTO;
import es.citagest.catalog.department.entity.DepartmentEntity;

public class DepartmentMapper {
    public static DepartmentDTO toDTO(DepartmentEntity entity){
        if(entity == null) return null;
        return new DepartmentDTO(entity.getId(), entity.getName(), entity.getCode(), entity.getDescription());
    }

    public static DepartmentEntity toEntity(DepartmentDTO dto){
        if (dto == null) return null;
        return new DepartmentEntity(dto.getName(), dto.getCode(), dto.getDescription());
    }
}
