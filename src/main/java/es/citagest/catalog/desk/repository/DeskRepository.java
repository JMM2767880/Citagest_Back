package es.citagest.catalog.desk.repository;

import es.citagest.catalog.desk.entity.DeskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeskRepository extends JpaRepository<DeskEntity, Long> {
    public List<DeskEntity> findByEnabled(Boolean enabled);
}
