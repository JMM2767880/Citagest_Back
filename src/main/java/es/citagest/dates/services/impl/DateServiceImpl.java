package es.citagest.dates.services.impl;

import es.citagest.catalog.state.entity.StateEntity;
import es.citagest.catalog.state.repository.StateRepository;
import es.citagest.dates.dto.DateDTO;
import es.citagest.dates.dto.DatesPerDayDTO;
import es.citagest.dates.entity.DateEntity;
import es.citagest.dates.mapper.DateMapper;
import es.citagest.dates.repository.DateRepository;
import es.citagest.dates.services.DateService;
import es.citagest.user.entity.UserEntity;
import es.citagest.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DateServiceImpl implements DateService {

    private final DateRepository dateRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    public DateServiceImpl(DateRepository dateRepository, StateRepository stateRepository, UserRepository userRepository) {
        this.dateRepository = dateRepository;
        this.stateRepository = stateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<DateDTO> createDates(List<DateDTO> dates) {
        List<DateEntity> entitiesDates = new ArrayList<>();

        for (DateDTO dateDTO : dates) {
            DateEntity entity = new DateEntity();

            entity.setId(dateDTO.getId());
            entity.setNameClient(dateDTO.getNameClient());
            entity.setEmailClient(dateDTO.getEmailClient());
            entity.setDateTime(dateDTO.getDateTime());

            if (dateDTO.getState() == null || dateDTO.getState().getId() == null) {
                throw new IllegalArgumentException("El estado es obligatorio");
            }

            if (dateDTO.getUser() == null || dateDTO.getUser().getId() == null) {
                throw new IllegalArgumentException("El usuario es obligatorio");
            }

            StateEntity stateEntity = stateRepository.findById(dateDTO.getState().getId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe el estado con id " + dateDTO.getState().getId()));

            UserEntity userEntity = userRepository.findById(dateDTO.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id " + dateDTO.getUser().getId()));

            entity.setState(stateEntity);
            entity.setUser(userEntity);

            if(!existsByUserIdAndDateTime(dateDTO.getUser().getId(), entity.getDateTime())) entitiesDates.add(entity);
        }

        List<DateEntity> savedDates = dateRepository.saveAll(entitiesDates);
        List<DateDTO> savedDatesDTO = new ArrayList<>();

        for (DateEntity savedDate : savedDates) {
            savedDatesDTO.add(DateMapper.toDTO(savedDate));
        }

        for (DateEntity date: savedDates){
            date.setDateCode("T-" + String.format("%04d", date.getId()));
            dateRepository.save(date);
        }

        return savedDatesDTO;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DateDTO> getDatesByUser(Long userId) {
        List<DateEntity> dates = dateRepository.findByUserEntityId(userId);
        List<DateDTO> datesDTO = new ArrayList<>();

        for (DateEntity date : dates) {
            datesDTO.add(DateMapper.toDTO(date));
        }

        return datesDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DateDTO> getDatesByDay(LocalDate day) {
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

        List<DateEntity> dates = dateRepository.findByDateTimeBetween(startOfDay, endOfDay);
        List<DateDTO> datesDTO = new ArrayList<>();

        for (DateEntity date : dates) {
            datesDTO.add(DateMapper.toDTO(date));
        }

        return datesDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DateDTO> getDatesByUserAndDay(Long userId, LocalDate day) {
        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

        List<DateEntity> dates = dateRepository.findByUserEntityIdAndDateTimeBetween(userId, startOfDay, endOfDay);
        List<DateDTO> datesDTO = new ArrayList<>();

        for (DateEntity date : dates) {
            datesDTO.add(DateMapper.toDTO(date));
        }

        return datesDTO;
    }

    @Override
    public List<DateDTO> getAvailableDatesByDepartment(Long departmentId) {
        LocalDateTime now = LocalDateTime.now();

        List<DateEntity> dates = dateRepository.findAvailableDatesByDepartmentIdFromNow(departmentId, now);
        List<DateDTO> datesDTO = new ArrayList<>();

        for (DateEntity date : dates) {
            datesDTO.add(DateMapper.toDTO(date));
        }

        return datesDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUserIdAndDateTime(Long userId, LocalDateTime datetime){
        return dateRepository.existsByUserIdAndDateTime(userId, datetime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DateDTO> findByDepartmentIdAndDateTime(
            LocalDateTime dateTimeStart,
            Long departmentId
    ) {
        LocalDateTime startOfDay =
                dateTimeStart.toLocalDate().atStartOfDay();

        LocalDateTime startOfNextDay = startOfDay.plusDays(1);

        LocalDateTime now = LocalDateTime.now();

        // Si el día ya ha terminado, no hay horarios reservables.
        if (!startOfNextDay.isAfter(now)) {
            return List.of();
        }

        // Para hoy buscamos desde ahora; para días futuros, desde las 00:00.
        LocalDateTime searchStart =
                startOfDay.isBefore(now) ? now : startOfDay;

        return dateRepository.findByDepartmentIdAndDateTime(
                        searchStart,
                        startOfNextDay,
                        departmentId
                )
                .stream()
                .map(DateMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public DateDTO setClientDate(
            LocalDateTime dateTime,
            Long departmentId,
            String nameClient
    ) {
        if (dateTime == null || departmentId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha y el departamento son obligatorios"
            );
        }

        if (nameClient == null || nameClient.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre del cliente es obligatorio"
            );
        }

        String normalizedName = nameClient.strip();

        if (normalizedName.length() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre no puede superar los 50 caracteres"
            );
        }

        if (!dateTime.isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cita debe ser posterior al momento actual"
            );
        }

        List<DateEntity> availableDates =
                dateRepository.findByDepartmentIdAndDateTimeToModify(
                        dateTime,
                        departmentId
                );

        if (availableDates.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya no quedan citas disponibles para esa fecha y hora"
            );
        }

        DateEntity date = availableDates.get(0);

        date.setNameClient(normalizedName);
        date.setDateCode("T-" + String.format("%04d", date.getId()));

        DateEntity savedDate = dateRepository.save(date);

        return DateMapper.toDTO(savedDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatesPerDayDTO> countTotalDatesGroupedByDay(LocalDateTime dateTime, Long userId){
        LocalDate firstDayOfMonth = dateTime.toLocalDate().withDayOfMonth(1);

        LocalDateTime dateTimeStart = firstDayOfMonth.atStartOfDay();
        LocalDateTime dateTimeFinish = firstDayOfMonth.plusMonths(1).atStartOfDay();
        return dateRepository.countTotalDatesGroupedByDay(dateTimeStart, dateTimeFinish, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DateDTO> findByDateTimeBetweenAndUserEntityId(LocalDateTime dateTime, Long id) {
        LocalDateTime dateTimeStart = dateTime.toLocalDate().atStartOfDay();
        LocalDateTime dateTimeFinish = dateTime.toLocalDate().plusDays(1).atStartOfDay();

        List<Long> excludedStates = List.of(4L, 5L);

        return dateRepository
                .findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserEntityIdAndStateEntityIdNotIn(
                        dateTimeStart,
                        dateTimeFinish,
                        id,
                        excludedStates
                )
                .stream()
                .map(DateMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public DateDTO setClientToDate(DateDTO dateDTO, Long sessionUserId) {
        if (dateDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        DateEntity date = getOwnedDate(dateDTO.getId(), sessionUserId);

        String nameClient = dateDTO.getNameClient();

        if (nameClient == null || nameClient.isBlank()
                || nameClient.strip().length() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre debe contener entre 1 y 50 caracteres"
            );
        }

        date.setNameClient(nameClient.strip());

        return DateMapper.toDTO(dateRepository.save(date));
    }

    @Override
    @Transactional
    public DateDTO setEmptyClientToDate(
            DateDTO dateDTO,
            Long sessionUserId
    ) {
        if (dateDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        DateEntity date = getOwnedDate(dateDTO.getId(), sessionUserId);

        date.setNameClient("");

        return DateMapper.toDTO(dateRepository.save(date));
    }

    @Override
    @Transactional(readOnly = true)
    public DateDTO findByClientName(String clientName, Long userId){
        if (clientName.equals("") || clientName == null || userId == null) return null;
        Optional<DateEntity> dateSearch = dateRepository.findByNameClientAndUserEntityId(clientName, userId);
        if (dateSearch.isEmpty()) return null;
        return DateMapper.toDTO(dateSearch.get());
    }

    private DateEntity getOwnedDate(Long dateId, Long sessionUserId) {
        if (sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (dateId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador de la cita es obligatorio"
            );
        }

        DateEntity date = dateRepository.findById(dateId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La cita no existe"
                ));

        if (!sessionUserId.equals(date.getUser().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes modificar citas de otra empleada"
            );
        }

        return date;
    }
}
