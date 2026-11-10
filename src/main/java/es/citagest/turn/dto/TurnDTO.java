package es.citagest.turn.dto;

import es.citagest.catalog.state.dto.StateDTO;
import es.citagest.dates.dto.DateDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TurnDTO {
    private Long id;

    @NotNull
    private DateDTO date;

    @NotNull
    private LocalDateTime dateUpdate;

    @NotNull
    private StateDTO state;

    public TurnDTO() {}

    public TurnDTO(Long id, DateDTO date, LocalDateTime dateUpdate, StateDTO state) {
        this.id = id;
        this.date = date;
        this.dateUpdate = dateUpdate;
        this.state = state;
    }

    public TurnDTO(DateDTO date, LocalDateTime dateUpdate, StateDTO state) {
        this.date = date;
        this.dateUpdate = dateUpdate;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TurnDTO{" +
                "id=" + id +
                ", date=" + date +
                ", dateUpdate=" + dateUpdate +
                ", state=" + state +
                '}';
    }
}
