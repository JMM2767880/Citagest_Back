package es.citagest.dates.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatesPerDayDTO {
    private LocalDate day;
    private Long totalDates;
    private Long assignedDates;
    private Long freeDates;

    public DatesPerDayDTO(Object day, Long totalDates, Long assignedDates, Long freeDates) {
        this.day = convertToLocalDate(day);
        this.totalDates = totalDates;
        this.assignedDates = assignedDates;
        this.freeDates = freeDates;
    }

    private LocalDate convertToLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime().toLocalDate();
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.toLocalDate();
        }
        throw new IllegalArgumentException("Tipo de fecha no soportado: " + value);
    }

    public LocalDate getDay() {
        return day;
    }

    public Long getTotalDates() {
        return totalDates;
    }

    public Long getAssignedDates() {
        return assignedDates;
    }

    public Long getFreeDates() {
        return freeDates;
    }
}