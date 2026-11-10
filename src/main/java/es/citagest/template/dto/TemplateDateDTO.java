package es.citagest.template.dto;

import es.citagest.user.dto.UserDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class TemplateDateDTO {
    private Long id;

    @NotNull
    @Min(1)
    @Max(7)
    private Integer weekday;

    @NotNull
    private LocalTime hour;

    @NotNull
    private UserDTO user;

    @NotNull
    @Min(1)
    @Max(2)
    private Integer numberTemplate;

    public TemplateDateDTO() {}

    public TemplateDateDTO(Long id, Integer weekday, LocalTime hour, UserDTO user, Integer numberTemplate) {
        this.id = id;
        this.weekday = weekday;
        this.hour = hour;
        this.user = user;
        this.numberTemplate = numberTemplate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getNumberTemplate() {
        return numberTemplate;
    }

    public void setNumberTemplate(Integer numberTemplate) {
        this.numberTemplate = numberTemplate;
    }

    @Override
    public String toString() {
        return "TemplateDateDTO{" +
                "id=" + id +
                ", weekday=" + weekday +
                ", hour=" + hour +
                ", user=" + user +
                ", numberTemplate=" + numberTemplate +
                '}';
    }
}
