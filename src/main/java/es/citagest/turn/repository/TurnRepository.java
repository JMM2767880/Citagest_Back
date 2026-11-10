package es.citagest.turn.repository;

import es.citagest.turn.dto.TurnDTO;
import es.citagest.turn.entity.TurnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TurnRepository extends JpaRepository<TurnEntity, Long> {
    List<TurnEntity> findByDateDateTimeGreaterThanEqualAndDateDateTimeLessThanAndDateUserEntityIdAndStateIdInOrderByDateDateTimeAsc(
            LocalDateTime start,
            LocalDateTime end,
            Long userId,
            List<Long> stateIds
    );
    List<TurnEntity> findByDateDateTimeGreaterThanEqualAndDateDateTimeLessThanAndDateUserEntityIdAndStateIdInAndDateNameClientNotOrderByDateDateTimeAsc(
            LocalDateTime start,
            LocalDateTime end,
            Long userId,
            List<Long> stateIds,
            String nameClient
    );
    List<TurnEntity> findByDateUserEntityIdAndStateId(Long userId, Long stateId);
}
