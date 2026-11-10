package es.citagest.template.entity;

import es.citagest.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Entity
@Table(
        name = "template_date",
        check = {
                @CheckConstraint(name = "chk_number_template_template_date", constraint = "number_template in (1, 2)"),
                @CheckConstraint(name = "chk_weekday_template_date", constraint = "weekday between 1 and 7")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_template_date", columnNames = {"weekday", "hour", "user_id", "number_template"})
        }
)
public class TemplateDateEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @NotNull
    @Min(1)
    @Max(7)
    @Column(
            name = "weekday",
            nullable = false
    )
    private Integer weekday;

    @NotNull
    @Column(
            name = "hour",
            nullable = false
    )
    private LocalTime hour;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_template_date")
    )
    private UserEntity userEntity;

    @NotNull
    @Min(1)
    @Max(2)
    @Column(
            name = "number_template",
            nullable = false
    )
    private Integer numberTemplate;

    public TemplateDateEntity(){}

    public TemplateDateEntity(Long id, Integer weekday, LocalTime hour, UserEntity userEntity, Integer numberTemplate) {
        this.id = id;
        this.weekday = weekday;
        this.hour = hour;
        this.userEntity = userEntity;
        this.numberTemplate = numberTemplate;
    }

    public TemplateDateEntity(Integer weekday, LocalTime hour, UserEntity userEntity, Integer numberTemplate) {
        this.weekday = weekday;
        this.hour = hour;
        this.userEntity = userEntity;
        this.numberTemplate = numberTemplate;
    }

    public Long getId() {
        return id;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Integer getNumberTemplate() {
        return numberTemplate;
    }

    public void setNumberTemplate(Integer numberTemplate) {
        this.numberTemplate = numberTemplate;
    }

    @Override
    public String toString() {
        return "TemplateDate{" +
                "numberTemplate=" + numberTemplate +
                ", hour=" + hour +
                ", weekday=" + weekday +
                ", id=" + id +
                '}';
    }
}
