package es.citagest.user.controller;

import es.citagest.user.dto.LoginRequestDTO;
import es.citagest.user.dto.UserDTO;
import es.citagest.user.service.AuthService;
import es.citagest.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO request, HttpSession session) {
        UserDTO user = authService.login(request.getUsername(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("name", user.getName());
        session.setAttribute("lastname", user.getLastname());
        session.setAttribute("username", user.getUsername());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(HttpSession session) {
        Object userId = session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = new UserDTO();
        user.setId((Long) session.getAttribute("userId"));
        user.setName((String) session.getAttribute("name"));
        user.setLastname((String) session.getAttribute("lastname"));
        user.setUsername((String) session.getAttribute("username"));

        UserDTO userDTO = userService.findById(user.getId());

        if (userDTO != null) {
            user.setDesk(userDTO.getDesk());
            user.setDepartment(userDTO.getDepartment());
        }

        return ResponseEntity.ok(user);
    }
}
