package es.citagest.catalog.desk.dto;

import es.citagest.turn.dto.TurnDTO;

import java.util.List;

public class TurnsDeskDTO {
    private DeskDTO desk;
    private List<TurnDTO> turns;

    public TurnsDeskDTO() {}

    public TurnsDeskDTO(DeskDTO desk, List<TurnDTO> turns) {
        this.desk = desk;
        this.turns = turns;
    }

    public DeskDTO getDesk() {
        return desk;
    }

    public void setDesk(DeskDTO desk) {
        this.desk = desk;
    }

    public List<TurnDTO> getTurns() {
        return turns;
    }

    public void setTurns(List<TurnDTO> turns) {
        this.turns = turns;
    }

    @Override
    public String toString() {
        return "TurnsDeskDTO{" +
                "desk=" + desk +
                ", turns=" + turns +
                '}';
    }
}
