package es.citagest.catalog.desk.dto;

import es.citagest.catalog.department.dto.DepartmentDTO;

import java.util.List;

public class DeskDepartmentDTO {
    private DepartmentDTO departmentDTO;
    private List<TurnsDeskDTO> turnsDeskDTOS;

    public DeskDepartmentDTO(){}

    public DeskDepartmentDTO(DepartmentDTO departmentDTO, List<TurnsDeskDTO> turnsDeskDTOS) {
        this.departmentDTO = departmentDTO;
        this.turnsDeskDTOS = turnsDeskDTOS;
    }

    public DepartmentDTO getDepartmentDTO() {
        return departmentDTO;
    }

    public void setDepartmentDTO(DepartmentDTO departmentDTO) {
        this.departmentDTO = departmentDTO;
    }

    public List<TurnsDeskDTO> getTurnsDeskDTOS() {
        return turnsDeskDTOS;
    }

    public void setTurnsDeskDTOS(List<TurnsDeskDTO> turnsDeskDTOS) {
        this.turnsDeskDTOS = turnsDeskDTOS;
    }

    @Override
    public String toString() {
        return "DeskDepartmentDTO{" +
                "departmentDTO=" + departmentDTO +
                ", deskDTOS=" + turnsDeskDTOS +
                '}';
    }
}
