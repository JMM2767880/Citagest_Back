package es.citagest.catalog.desk.controller;

import es.citagest.catalog.desk.dto.DeskDTO;
import es.citagest.catalog.desk.dto.DeskDepartmentDTO;
import es.citagest.catalog.desk.services.DeskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/desk")
public class DeskController {

    private DeskService deskService;

    public DeskController(DeskService deskService){
        this.deskService = deskService;
    }

    @GetMapping("/enabled")
    public ResponseEntity<List<DeskDepartmentDTO>> getDeskEnabled(){
        return ResponseEntity.ok(deskService.getDeskDepartments());
    }
}
