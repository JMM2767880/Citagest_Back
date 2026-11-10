package es.citagest.catalog.desk.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DeskDTO {
    @NotNull
    private Long id;

    @Size(max = 50)
    @NotNull
    private String name;

    @NotNull
    @Size(max = 50)
    private String code;

    @NotNull
    private Boolean enabled;

    public DeskDTO() {}

    public DeskDTO(Long id, String name, String code, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.enabled = enabled;
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
        return "DeskDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
