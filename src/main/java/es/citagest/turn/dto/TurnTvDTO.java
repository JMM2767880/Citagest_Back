package es.citagest.turn.dto;

import java.util.List;

public class TurnTvDTO {
    private List<TurnDTO> pendingTurns;
    private TurnDTO currentTurn;

    public TurnTvDTO() {
    }

    public TurnTvDTO(List<TurnDTO> pendingTurns, TurnDTO currentTurn) {
        this.pendingTurns = pendingTurns;
        this.currentTurn = currentTurn;
    }

    public List<TurnDTO> getPendingTurns() {
        return pendingTurns;
    }

    public void setPendingTurns(List<TurnDTO> pendingTurns) {
        this.pendingTurns = pendingTurns;
    }

    public TurnDTO getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(TurnDTO currentTurn) {
        this.currentTurn = currentTurn;
    }

    @Override
    public String toString() {
        return "TurnTvDTO{" +
                "pendingTurns=" + pendingTurns +
                ", currentTurn=" + currentTurn +
                '}';
    }
}
