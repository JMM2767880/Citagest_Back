package es.citagest.catalog.desk.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "desk"
)
public class DeskEntity {
    @Id
    @Column(
            name = "id",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Size(max = 50)
    @NotNull
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
    @Column(
            name = "enabled",
            nullable = false
    )
    Boolean enabled;

    public DeskEntity(){}

    public DeskEntity(String name, String code, Boolean enabled) {
        this.name = name;
        this.code = code;
        this.enabled = enabled;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "DeskEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
