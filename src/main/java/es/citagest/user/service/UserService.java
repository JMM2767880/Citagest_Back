package es.citagest.user.service;

import es.citagest.user.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
}
