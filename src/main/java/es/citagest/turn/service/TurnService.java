package es.citagest.turn.service;

import es.citagest.dates.dto.DateDTO;
import es.citagest.turn.dto.TurnDTO;
import java.util.List;

public interface TurnService {
    TurnDTO createTurn(DateDTO dateDTO);
    List<TurnDTO> getTurnsTodayByUser(Long userId);
    List<TurnDTO> getTurnsTodayByUserEmptyClient(Long userId);
    TurnDTO callTurn(TurnDTO turnDTO, Long sessionUserId);
    TurnDTO finishTurn(Long id, Long sessionUserId);
    TurnDTO discardTurn(Long id, Long sessionUserId);
}
