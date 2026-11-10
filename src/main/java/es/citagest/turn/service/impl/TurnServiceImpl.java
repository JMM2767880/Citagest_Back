package es.citagest.turn.service.impl;

import es.citagest.catalog.state.entity.StateEntity;
import es.citagest.catalog.state.repository.StateRepository;
import es.citagest.dates.dto.DateDTO;
import es.citagest.dates.entity.DateEntity;
import es.citagest.dates.repository.DateRepository;
import es.citagest.turn.dto.TurnDTO;
import es.citagest.turn.entity.TurnEntity;
import es.citagest.turn.mapper.TurnMapper;
import es.citagest.turn.repository.TurnRepository;
import es.citagest.turn.service.TurnService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TurnServiceImpl implements TurnService {
    private TurnRepository turnRepository;
    private DateRepository dateRepository;
    private StateRepository stateRepository;

    public TurnServiceImpl(TurnRepository turnRepository, DateRepository dateRepository, StateRepository stateRepository){
        this.turnRepository = turnRepository;
        this.dateRepository = dateRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    @Transactional
    public TurnDTO createTurn(DateDTO dateDTO){
        if(dateDTO == null) return null;
        Optional<DateEntity> date = dateRepository.findById(dateDTO.getId());
        if (!date.isPresent()) return null;
        Optional<StateEntity> state = stateRepository.findById(6L);
        if (!state.isPresent()) return null;
        return TurnMapper.toDTO(turnRepository.save(new TurnEntity(null, date.get(), LocalDateTime.now(), state.get())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnDTO> getTurnsTodayByUser(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        List<Long> statesId = Arrays.asList(6L, 7L);

        List<TurnEntity> turns = turnRepository
                .findByDateDateTimeGreaterThanEqualAndDateDateTimeLessThanAndDateUserEntityIdAndStateIdInOrderByDateDateTimeAsc(
                        startOfDay,
                        endOfDay,
                        userId,
                        statesId
                );

        List<TurnDTO> turnsDTO = new ArrayList<>();

        for (TurnEntity turn : turns) {
            turnsDTO.add(TurnMapper.toDTO(turn));
        }

        return turnsDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurnDTO> getTurnsTodayByUserEmptyClient(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        List<Long> statesId = Arrays.asList(6L, 7L);

        List<TurnEntity> turns = turnRepository
                .findByDateDateTimeGreaterThanEqualAndDateDateTimeLessThanAndDateUserEntityIdAndStateIdInAndDateNameClientNotOrderByDateDateTimeAsc(
                        startOfDay,
                        endOfDay,
                        userId,
                        statesId,
                        ""
                );

        List<TurnDTO> turnsDTO = new ArrayList<>();

        for (TurnEntity turn : turns) {
            turnsDTO.add(TurnMapper.toDTO(turn));
        }

        return turnsDTO;
    }

    @Override
    @Transactional
    public TurnDTO callTurn(TurnDTO turnDTO, Long sessionUserId) {
        if (turnDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TurnEntity turnEntity = getOwnedTurn(
                turnDTO.getId(),
                sessionUserId
        );

        Long userId = turnEntity.getDate().getUser().getId();

        StateEntity stateWaiting = stateRepository.findById(6L)
                .orElseThrow(() -> new IllegalArgumentException("No existe el estado 6"));

        StateEntity stateInProgress = stateRepository.findById(7L)
                .orElseThrow(() -> new IllegalArgumentException("No existe el estado 7"));

        List<TurnEntity> turnsInProgress = turnRepository.findByDateUserEntityIdAndStateId(userId, 7L);

        for (TurnEntity turn : turnsInProgress) {
            turn.setState(stateWaiting);
            turn.setDateUpdate(LocalDateTime.now());
        }

        turnEntity.setState(stateInProgress);
        turnEntity.setDateUpdate(LocalDateTime.now());

        turnRepository.saveAll(turnsInProgress);
        TurnEntity savedTurn = turnRepository.save(turnEntity);

        return TurnMapper.toDTO(savedTurn);
    }

    @Override
    @Transactional
    public TurnDTO finishTurn(Long id, Long sessionUserId) {
        TurnEntity turnEntity = getOwnedTurn(id, sessionUserId);

        StateEntity turnFinishState = stateRepository.findById(8L)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "No existe el estado de turno finalizado"
                ));

        StateEntity dateFinishState = stateRepository.findById(4L)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "No existe el estado de cita finalizada"
                ));

        turnEntity.setState(turnFinishState);
        turnEntity.setDateUpdate(LocalDateTime.now());

        turnEntity.getDate().setState(dateFinishState);

        TurnEntity savedTurn = turnRepository.save(turnEntity);

        return TurnMapper.toDTO(savedTurn);
    }

    @Override
    @Transactional
    public TurnDTO discardTurn(Long id, Long sessionUserId) {
        TurnEntity turnEntity = getOwnedTurn(id, sessionUserId);

        StateEntity turnDiscardState = stateRepository.findById(9L)
                .orElseThrow(() -> new IllegalArgumentException("No existe el estado de turno descartado con id 9"));

        StateEntity dateDiscardState = stateRepository.findById(5L)
                .orElseThrow(() -> new IllegalArgumentException("No existe el estado de cita descartada con id 5"));

        turnEntity.setState(turnDiscardState);
        turnEntity.setDateUpdate(LocalDateTime.now());

        if (turnEntity.getDate() != null) {
            turnEntity.getDate().setState(dateDiscardState);
        }

        TurnEntity savedTurn = turnRepository.save(turnEntity);

        return TurnMapper.toDTO(savedTurn);
    }

    private TurnEntity getOwnedTurn(Long turnId, Long sessionUserId) {
        if (sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (turnId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador del turno es obligatorio"
            );
        }

        TurnEntity turn = turnRepository.findById(turnId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El turno no existe"
                ));

        if (!sessionUserId.equals(turn.getDate().getUser().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes modificar turnos de otra empleada"
            );
        }

        return turn;
    }
}
