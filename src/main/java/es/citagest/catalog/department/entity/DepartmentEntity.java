package es.citagest.catalog.department.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "department"
)
public class DepartmentEntity {
    @Id
    @Column(
            name = "id",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "name",
            nullable = false,
            length = 50
    )
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "code",
            unique = true,
            nullable = false,
            length = 50
    )
    private String code;

    @NotNull
    @Size(max = 255)
    @Column(
            name = "description",
            nullable = false,
            length = 255
    )
    private String description;

    public DepartmentEntity(){}

    public DepartmentEntity(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
