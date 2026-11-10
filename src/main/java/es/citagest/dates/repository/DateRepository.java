package es.citagest.dates.repository;

import es.citagest.dates.dto.DatesPerDayDTO;
import es.citagest.dates.entity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

public interface DateRepository extends JpaRepository<DateEntity, Long> {
    List<DateEntity> findByUserEntityId(Long userId);
    List<DateEntity> findByDateTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    List<DateEntity> findByUserEntityIdAndDateTimeBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    @Query("""
        SELECT d
        FROM DateEntity d
        WHERE d.userEntity.departmentEntity.id = :departmentId
          AND (d.nameClient IS NULL OR d.nameClient = '')
          AND d.dateTime >= :now
        ORDER BY d.dateTime ASC
    """)
    List<DateEntity> findAvailableDatesByDepartmentIdFromNow(
            @Param("departmentId") Long departmentId,
            @Param("now") LocalDateTime now
    );

    @Query("""
        SELECT COUNT(d) > 0
        FROM DateEntity d
        WHERE d.userEntity.id = :userId
          AND d.dateTime = :dateTime
    """)
    boolean existsByUserIdAndDateTime(
            @Param("userId") Long userId,
            @Param("dateTime") LocalDateTime dateTime
    );

    @Query("""
        SELECT d
        FROM DateEntity d
        WHERE d.userEntity.departmentEntity.id = :departmentId
          AND (d.nameClient IS NULL OR d.nameClient = '')
          AND d.dateTime >= :dateTimeStart
          AND d.dateTime < :dateTimeFinish
        ORDER BY d.dateTime ASC
        """)
    List<DateEntity> findByDepartmentIdAndDateTime(
            @Param("dateTimeStart") LocalDateTime dateTimeStart,
            @Param("dateTimeFinish") LocalDateTime dateTimeFinish,
            @Param("departmentId") Long departmentId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT d
        FROM DateEntity d
        WHERE d.userEntity.departmentEntity.id = :departmentId
          AND d.dateTime = :dateTime
          AND (d.nameClient IS NULL OR d.nameClient = '')
          AND d.stateEntity.code NOT IN ('finish', 'discard')
        ORDER BY d.id ASC
        """)
    List<DateEntity> findByDepartmentIdAndDateTimeToModify(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("departmentId") Long departmentId
    );

    @Query("""
    SELECT new es.citagest.dates.dto.DatesPerDayDTO(
           FUNCTION('DATE', d.dateTime),
           COUNT(d),
           SUM(CASE WHEN d.nameClient IS NOT NULL AND d.nameClient <> '' THEN 1 ELSE 0 END),
           SUM(CASE WHEN d.nameClient IS NULL OR d.nameClient = '' THEN 1 ELSE 0 END)
       )
       FROM DateEntity d
       WHERE d.userEntity.id = :userId
         AND d.dateTime >= :startDateTime
         AND d.dateTime <= :finishDateTime
         AND d.stateEntity.code NOT IN ('finish', 'discard')
       GROUP BY FUNCTION('DATE', d.dateTime)
       ORDER BY FUNCTION('DATE', d.dateTime) ASC
       """)
    List<DatesPerDayDTO> countTotalDatesGroupedByDay(@Param("startDateTime") LocalDateTime startDateTime,
                                                         @Param("finishDateTime") LocalDateTime finishDateTime,
                                                         @Param("userId") Long userId);

    List<DateEntity> findByDateTimeBetweenAndUserEntityId(LocalDateTime start, LocalDateTime end, Long id);
    Optional<DateEntity> findTopByOrderByIdDesc();
    List<DateEntity> findByDateTimeGreaterThanEqualAndDateTimeLessThanAndUserEntityIdAndStateEntityIdNotIn(
            LocalDateTime start,
            LocalDateTime end,
            Long userId,
            List<Long> stateIds
    );
    Optional<DateEntity> findByNameClientAndUserEntityId(String nameClient, Long idUser);
}
