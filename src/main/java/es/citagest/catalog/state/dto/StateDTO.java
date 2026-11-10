package es.citagest.catalog.state.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StateDTO{
    public enum StateType {
        CITA,
        TURNO
    }

    private Long id;

    @Size(max = 50)
    private String name;

    private StateType type;

    @Size(max = 50)
    private String code;

    @Size(max = 255)
    private String description;

    public StateDTO() {}

    public StateDTO(Long id, String name, StateType type, String code, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }

    public void setId(Long id){
        this.id = id;
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
        return "StateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
