package es.citagest.template.controller;

import es.citagest.template.dto.TemplateDateDTO;
import es.citagest.template.service.TemplateDateService;
import es.citagest.user.dto.UserDTO;
import es.citagest.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template-dates")
public class TemplateDateController {

    private final TemplateDateService templateDateService;
    private final UserService userService;

    public TemplateDateController(TemplateDateService templateDateService, UserService userService) {
        this.templateDateService = templateDateService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<List<TemplateDateDTO>> createTemplateDates(
            @RequestParam Integer numberTemplate,
            @RequestBody List<TemplateDateDTO> templateDateDTOList,
            HttpSession session
    ) {
        UserDTO sessionUser = getSessionUser(session);

        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TemplateDateDTO> createdTemplateDates =
                templateDateService.createTemplateDates(
                        templateDateDTOList,
                        numberTemplate,
                        sessionUser.getId()
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTemplateDates);
    }

    @GetMapping
    public ResponseEntity<List<TemplateDateDTO>> getTemplateDatesByNumberTemplate(
            @RequestParam int numberTemplate,
            HttpSession session
    ) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TemplateDateDTO> templateDates =
                templateDateService.getTemplateDatesByUserIdAndNumberTemplate(sessionUser, numberTemplate);

        if (templateDates == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(templateDates);
    }

    @DeleteMapping("/{templateDateId}")
    public ResponseEntity<TemplateDateDTO> deleteTemplateDate(
            @PathVariable Long templateDateId,
            HttpSession session
    ) {
        UserDTO sessionUser = getSessionUser(session);

        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TemplateDateDTO deletedTemplateDate =
                templateDateService.deleteTemplateDate(
                        templateDateId,
                        sessionUser.getId()
                );

        return ResponseEntity.ok(deletedTemplateDate);
    }

    private UserDTO getSessionUser(HttpSession session) {
        if (session == null) {
            return null;
        }

        Object userIdObject = session.getAttribute("userId");
        if (!(userIdObject instanceof Long userId)) {
            return null;
        }

        return userService.findById(userId);
    }
}
