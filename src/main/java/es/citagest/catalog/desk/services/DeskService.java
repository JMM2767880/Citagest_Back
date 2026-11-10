package es.citagest.catalog.desk.services;

import es.citagest.catalog.desk.dto.DeskDTO;
import es.citagest.catalog.desk.dto.DeskDepartmentDTO;

import java.util.List;

public interface DeskService {
    List<DeskDTO> getEnabledDesk();
    List<DeskDepartmentDTO> getDeskDepartments();
}
