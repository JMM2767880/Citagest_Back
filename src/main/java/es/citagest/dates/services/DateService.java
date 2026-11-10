package es.citagest.dates.services;

import es.citagest.dates.dto.DateDTO;
import es.citagest.dates.dto.DatesPerDayDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateService {
    List<DateDTO> createDates(List<DateDTO> dates);
    List<DateDTO> getDatesByUser(Long userId);
    List<DateDTO> getDatesByDay(LocalDate day);
    List<DateDTO> getDatesByUserAndDay(Long userId, LocalDate day);
    List<DateDTO> getAvailableDatesByDepartment(Long departmentId);
    boolean existsByUserIdAndDateTime(Long UserId, LocalDateTime datetime);
    List<DateDTO> findByDepartmentIdAndDateTime(LocalDateTime dateTimeStart, Long departmentId);
    DateDTO setClientDate(LocalDateTime dateTime, Long departmentId, String nameClient);
    List<DatesPerDayDTO> countTotalDatesGroupedByDay(LocalDateTime dateTime, Long userId);
    List<DateDTO> findByDateTimeBetweenAndUserEntityId(LocalDateTime dateTime, Long id);
    DateDTO setClientToDate(DateDTO date, Long sessionUserId);
    DateDTO setEmptyClientToDate(DateDTO dateDTO, Long sessionUserId);
    DateDTO findByClientName(String clientName, Long userId);
}
