package es.citagest.catalog.department.services;

import es.citagest.catalog.department.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentService {
    List<DepartmentEntity> findAll();
}
