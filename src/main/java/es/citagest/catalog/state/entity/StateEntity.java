package es.citagest.catalog.state.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "state",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_type_code_state",
                        columnNames = {"type", "code"}
                )
        },
        check = {
                @CheckConstraint(
                        name = "chk_type_state",
                        constraint = "type in ('CITA', 'TURNO')"
                )
        }
)
public class StateEntity {
    public enum StateType {
        CITA,
        TURNO
    }

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
    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "type",
            nullable = false,
            length = 20
    )
    private StateType type;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "code",
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

    public StateEntity(){}

    public StateEntity(String name, StateType type, String code, String description) {
        this.name = name;
        this.type = type;
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

    public StateType getType() {
        return type;
    }

    public void setType(StateType type) {
        this.type = type;
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
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
