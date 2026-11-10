package es.citagest.catalog.language.service;

import es.citagest.catalog.language.dto.LanguageDTO;

import java.util.List;

public interface LanguageService {
    List<LanguageDTO> getLanguages();
}
