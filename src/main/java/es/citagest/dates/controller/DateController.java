package es.citagest.dates.controller;

import es.citagest.dates.dto.DateDTO;
import es.citagest.dates.dto.DatesPerDayDTO;
import es.citagest.dates.services.DateService;
import es.citagest.turn.service.TurnService;
import es.citagest.user.dto.UserDTO;
import es.citagest.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/dates")
public class DateController {

    private final DateService dateService;
    private final UserService userService;
    private final TurnService turnService;

    public DateController(DateService dateService, UserService userService, TurnService turnService) {
        this.dateService = dateService;
        this.userService = userService;
        this.turnService = turnService;
    }

    @PostMapping
    public ResponseEntity<List<DateDTO>> createDates(@RequestBody List<DateDTO> dateDTOList, HttpSession session) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (dateDTOList == null || dateDTOList.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        dateDTOList.forEach(dateDTO -> dateDTO.setUser(sessionUser));

        List<DateDTO> createdDates = dateService.createDates(dateDTOList);
        for(DateDTO dateDTO: createdDates){
            turnService.createTurn(dateDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDates);
    }

    @GetMapping("/user")
    public ResponseEntity<List<DateDTO>> getDatesByUser(HttpSession session) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<DateDTO> dates = dateService.getDatesByUser(sessionUser.getId());
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/day")
    public ResponseEntity<List<DateDTO>> getDatesByDay(@RequestParam LocalDate day, HttpSession session) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<DateDTO> dates = dateService.getDatesByDay(day);
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/user/day")
    public ResponseEntity<List<DateDTO>> getDatesByUserAndDay(@RequestParam LocalDate day, HttpSession session) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<DateDTO> dates = dateService.getDatesByUserAndDay(sessionUser.getId(), day);
        return ResponseEntity.ok(dates);
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

    @GetMapping("/available/{departmentId}")
    public ResponseEntity<List<DateDTO>> getAvailableDatesByDepartment(@PathVariable Long departmentId) {
        List<DateDTO> dates = dateService.getAvailableDatesByDepartment(departmentId).stream()
                .limit(10)
                .toList();
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/available/{departmentId}/{dateTime}")
    public ResponseEntity<List<DateDTO>> findByDepartmentIdAndDateTime(@PathVariable Long departmentId, @PathVariable LocalDateTime dateTime) {
        List<DateDTO> dates = dateService.findByDepartmentIdAndDateTime(dateTime, departmentId).stream()
                .limit(10)
                .toList();
        return ResponseEntity.ok(dates);
    }

    @PutMapping
    public ResponseEntity<DateDTO> setClientDate(@RequestBody DateDTO date) {
        DateDTO dateModify = dateService.setClientDate(date.getDateTime(), date.getUser().getDepartment().getId(), date.getNameClient());
        return ResponseEntity.ok(dateModify);
    }

    @GetMapping("/totalDates/{dateTime}")
    public ResponseEntity<List<DatesPerDayDTO>> getTotalDatesByUserAndDay(@PathVariable LocalDateTime dateTime, HttpSession session) {
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<DatesPerDayDTO> dates = dateService.countTotalDatesGroupedByDay(dateTime, sessionUser.getId());
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/{dateTime}")
    public ResponseEntity<List<DateDTO>> getDates(@PathVariable LocalDateTime dateTime, HttpSession session){
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(dateService.findByDateTimeBetweenAndUserEntityId(dateTime, sessionUser.getId()));
    }

    @GetMapping("/nameClient/{clientName}")
    public ResponseEntity<DateDTO> getDates(@PathVariable String clientName, HttpSession session){
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(dateService.findByClientName(clientName, sessionUser.getId()));
    }

    @PutMapping("/setClient")
    public ResponseEntity<DateDTO> setClient(@RequestBody DateDTO dateDTO, HttpSession session){
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(
                dateService.setClientToDate(dateDTO, sessionUser.getId())
        );
    }

    @PutMapping("/setEmptyClient")
    public ResponseEntity<DateDTO> setEmptyClientToDate(@RequestBody DateDTO dateDTO, HttpSession session){
        UserDTO sessionUser = getSessionUser(session);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(
                dateService.setEmptyClientToDate(dateDTO, sessionUser.getId())
        );
    }
}