package es.citagest.catalog.department.services.impl;

import es.citagest.catalog.department.entity.DepartmentEntity;
import es.citagest.catalog.department.repository.DepartmentRepository;
import es.citagest.catalog.department.services.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentEntity> findAll() {
        return departmentRepository.findAll();
    }
}