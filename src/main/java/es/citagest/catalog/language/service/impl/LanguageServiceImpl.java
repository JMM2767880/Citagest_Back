package es.citagest.catalog.language.service.impl;

import es.citagest.catalog.language.dto.LanguageDTO;
import es.citagest.catalog.language.mapper.LanguageMapper;
import es.citagest.catalog.language.repository.LanguageRepository;
import es.citagest.catalog.language.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageDTO> getLanguages(){
        return languageRepository.findAll()
                .stream()
                .map(LanguageMapper::toDTO)
                .toList();
    }
}
