package es.citagest.turn.controller;

import es.citagest.catalog.desk.dto.DeskDepartmentDTO;
import es.citagest.catalog.desk.services.DeskService;
import es.citagest.turn.dto.TurnDTO;
import es.citagest.turn.dto.TurnTvDTO;
import es.citagest.turn.service.TurnService;
import es.citagest.turn.service.TurnTvStreamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/turn")
public class TurnController {
    private final TurnService turnService;
    private final DeskService deskService;

    public TurnController(TurnService turnService, DeskService deskService){
        this.turnService = turnService;
        this.deskService = deskService;
    }

    @GetMapping("/today")
    public ResponseEntity<List<TurnDTO>> getTurnsToday(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<TurnDTO> turns = turnService.getTurnsTodayByUser(userId);
        return ResponseEntity.ok(turns);
    }

    @GetMapping("/screen/today")
    public ResponseEntity<List<DeskDepartmentDTO>> getDeskEnabled(){
        return ResponseEntity.ok(deskService.getDeskDepartments());
    }

    @PutMapping("/call")
    public ResponseEntity<TurnDTO> callTurn(@RequestBody TurnDTO turnDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TurnDTO updatedTurn = turnService.callTurn(turnDTO, userId);
        return ResponseEntity.ok(updatedTurn);
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<TurnDTO> finishTurn(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TurnDTO updatedTurn = turnService.finishTurn(id, userId);
        return ResponseEntity.ok(updatedTurn);
    }

    @PutMapping("/discard/{id}")
    public ResponseEntity<TurnDTO> discardTurn(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TurnDTO updatedTurn = turnService.discardTurn(id, userId);
        return ResponseEntity.ok(updatedTurn);
    }
}
