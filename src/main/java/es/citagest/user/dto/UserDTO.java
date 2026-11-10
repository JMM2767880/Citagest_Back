package es.citagest.user.dto;

import es.citagest.catalog.department.dto.DepartmentDTO;
import es.citagest.catalog.desk.dto.DeskDTO;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private Long id;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String lastname;

    @Size(max = 50)
    private String username;

    private DepartmentDTO department;

    private DeskDTO desk;

    public UserDTO() {}

    public UserDTO(Long id, String name, String lastname, String username, DepartmentDTO department, DeskDTO desk) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.department = department;
        this.desk = desk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public DeskDTO getDesk() {
        return desk;
    }

    public void setDesk(DeskDTO desk) {
        this.desk = desk;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", department=" + department +
                ", desk=" + desk +
                '}';
    }
}
