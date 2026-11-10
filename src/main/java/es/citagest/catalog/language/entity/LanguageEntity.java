package es.citagest.catalog.language.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(
        name = "language"
)
public class LanguageEntity {
    @Size(max = 5)
    @Id
    @Column(
            name = "code",
            nullable = false,
            length = 5
    )
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(
            name = "name",
            nullable = false,
            length = 50
    )
    private String name;

    public LanguageEntity() {}

    public LanguageEntity(String code, String name) {
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LanguageEntity languageEntity = (LanguageEntity) o;
        return Objects.equals(code, languageEntity.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Language{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
