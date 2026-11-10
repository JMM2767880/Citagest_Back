package es.citagest.user.mapper;

import es.citagest.catalog.department.mapper.DepartmentMapper;
import es.citagest.catalog.desk.mapper.DeskMapper;
import es.citagest.user.dto.UserDTO;
import es.citagest.user.entity.UserEntity;

public class UserMapper {
    public static UserDTO toDTO(UserEntity entity){
        if (entity == null) return null;
        return new UserDTO(entity.getId(), entity.getName(), entity.getLastname(), entity.getUsername(), DepartmentMapper.toDTO(entity.getDepartmentEntity()), DeskMapper.toDTO(entity.getDeskEntity()));
    }

    public static UserEntity toEntity(UserDTO dto){
        if (dto == null) return null;
        return new UserEntity(dto.getName(), dto.getLastname(), dto.getUsername(), DepartmentMapper.toEntity(dto.getDepartment()), DeskMapper.toEntity(dto.getDesk()));
    }
}
