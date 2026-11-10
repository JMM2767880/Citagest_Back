package es.citagest.catalog.state.repository;

import es.citagest.catalog.state.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Long> {
}
