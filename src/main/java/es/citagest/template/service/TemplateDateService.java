package es.citagest.template.service;

import es.citagest.template.dto.TemplateDateDTO;
import es.citagest.user.dto.UserDTO;

import java.util.List;

public interface TemplateDateService {
    List<TemplateDateDTO> createTemplateDates(
            List<TemplateDateDTO> templatesDates,
            Integer numberTemplate,
            Long sessionUserId
    );
    List<TemplateDateDTO> getTemplateDatesByUserIdAndNumberTemplate(UserDTO userDTO, int numberTemplate);
    TemplateDateDTO deleteTemplateDate(
            Long templateDateId,
            Long sessionUserId
    );
    void deleteByUserAndNumberTemplate(Long userId, Integer numberTemplate);
}
