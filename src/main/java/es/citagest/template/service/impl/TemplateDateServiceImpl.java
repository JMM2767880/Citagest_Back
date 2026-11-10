package es.citagest.template.service.impl;

import es.citagest.template.dto.TemplateDateDTO;
import es.citagest.template.entity.TemplateDateEntity;
import es.citagest.template.mapper.TemplateDateMapper;
import es.citagest.template.repository.TemplateDateRepository;
import es.citagest.template.service.TemplateDateService;
import es.citagest.user.dto.UserDTO;
import es.citagest.user.entity.UserEntity;
import es.citagest.user.repository.UserRepository;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TemplateDateServiceImpl implements TemplateDateService {
    private final TemplateDateRepository templateDateRepository;
    private final UserRepository userRepository;

    public TemplateDateServiceImpl(TemplateDateRepository templateDateRepository, UserRepository userRepository){
        this.templateDateRepository = templateDateRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TemplateDateDTO> createTemplateDates(
            List<TemplateDateDTO> templateDateDTOList,
            Integer numberTemplate,
            Long sessionUserId
    ) {
        if (sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (numberTemplate == null
                || numberTemplate < 1
                || numberTemplate > 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La plantilla debe ser 1 o 2"
            );
        }

        if (templateDateDTOList == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La lista de horarios es obligatoria"
            );
        }

        UserEntity user = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED
                ));

        List<TemplateDateEntity> newEntries = new ArrayList<>();
        Set<String> keys = new HashSet<>();

        // Validar toda la lista antes de borrar la plantilla anterior.
        for (TemplateDateDTO dto : templateDateDTOList) {
            if (dto == null
                    || dto.getWeekday() == null
                    || dto.getWeekday() < 1
                    || dto.getWeekday() > 7
                    || dto.getHour() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Cada horario debe tener un día entre 1 y 7 y una hora"
                );
            }

            if (dto.getNumberTemplate() != null
                    && !numberTemplate.equals(dto.getNumberTemplate())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Los horarios deben pertenecer a la plantilla seleccionada"
                );
            }

            String key = dto.getWeekday() + "|" + dto.getHour();

            if (!keys.add(key)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Hay horarios repetidos en el mismo día"
                );
            }

            newEntries.add(new TemplateDateEntity(
                    dto.getWeekday(),
                    dto.getHour(),
                    user,
                    numberTemplate
            ));
        }

        templateDateRepository.deleteByUserEntityIdAndNumberTemplate(
                sessionUserId,
                numberTemplate
        );

        // Ejecutar los borrados antes de insertar horarios equivalentes.
        templateDateRepository.flush();

        List<TemplateDateEntity> savedEntries =
                templateDateRepository.saveAllAndFlush(newEntries);

        return savedEntries.stream()
                .map(TemplateDateMapper::toDTO)
                .toList();
    }

    public List<TemplateDateDTO> getTemplateDatesByUserIdAndNumberTemplate(UserDTO userDTO, int numberTemplate){
        if (userDTO == null || (numberTemplate < 1 || numberTemplate > 2)) return null;
        return templateDateRepository.findByUserEntityIdAndNumberTemplate(userDTO.getId(), numberTemplate).stream().map(TemplateDateMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public TemplateDateDTO deleteTemplateDate(
            Long templateDateId,
            Long sessionUserId
    ) {
        if (sessionUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (templateDateId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El identificador de la entrada es obligatorio"
            );
        }

        TemplateDateEntity templateDate =
                templateDateRepository.findById(templateDateId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "La entrada de plantilla no existe"
                        ));

        if (!sessionUserId.equals(templateDate.getUser().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No puedes borrar entradas de otra empleada"
            );
        }

        TemplateDateDTO deletedTemplateDate =
                TemplateDateMapper.toDTO(templateDate);

        templateDateRepository.delete(templateDate);

        return deletedTemplateDate;
    }

    @Override
    @Transactional
    public void deleteByUserAndNumberTemplate(Long userId, Integer numberTemplate){
        templateDateRepository.deleteByUserEntityIdAndNumberTemplate(userId, numberTemplate);
    }
}
