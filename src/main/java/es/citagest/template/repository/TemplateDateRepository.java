package es.citagest.template.repository;

import es.citagest.template.entity.TemplateDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.event.ListDataEvent;
import java.time.LocalTime;
import java.util.List;

public interface TemplateDateRepository extends JpaRepository<TemplateDateEntity, Long> {
    List<TemplateDateEntity> findByUserEntityIdAndNumberTemplate(Long userId, Integer numberTemplate);
    boolean existsByUserEntityIdAndWeekdayAndHourAndNumberTemplate(Long userId, Integer weekday, LocalTime hour, Integer numberTemplate);
    void deleteByUserEntityIdAndNumberTemplate(Long userId, Integer numberTemplate);
}
