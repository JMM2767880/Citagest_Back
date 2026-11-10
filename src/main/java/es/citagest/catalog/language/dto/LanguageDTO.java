package es.citagest.catalog.language.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LanguageDTO {
    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    public LanguageDTO() {}

    public LanguageDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LanguageDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
