package es.citagest.catalog.language.repository;

import es.citagest.catalog.language.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, String> {
}
