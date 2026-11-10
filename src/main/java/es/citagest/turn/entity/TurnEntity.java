package es.citagest.turn.entity;

import es.citagest.catalog.state.entity.StateEntity;
import es.citagest.dates.entity.DateEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "turns"
)
public class TurnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "date_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_date_turn")
    )
    private DateEntity date;

    @NotNull
    @Column(
            name = "date_update",
            nullable = false
    )
    private LocalDateTime dateUpdate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state", nullable = false, foreignKey = @ForeignKey(name = "fk_state_turn"))
    private StateEntity state;

    public TurnEntity(){}

    public TurnEntity(Long id, DateEntity date, LocalDateTime dateUpdate, StateEntity state) {
        this.id = id;
        this.date = date;
        this.dateUpdate = dateUpdate;
        this.state = state;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public DateEntity getDate() {
        return date;
    }

    public void setDate(DateEntity date) {
        this.date = date;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TurnEntity{" +
                "id=" + id +
                ", dateUpdate=" + dateUpdate +
                '}';
    }
}
