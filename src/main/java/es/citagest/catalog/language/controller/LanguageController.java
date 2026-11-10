package es.citagest.catalog.language.controller;

import es.citagest.catalog.language.dto.LanguageDTO;
import es.citagest.catalog.language.service.LanguageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService){
        this.languageService = languageService;
    }

    @GetMapping("/all")
    public List<LanguageDTO> getLanguages(){
        return languageService.getLanguages();
    }
}
