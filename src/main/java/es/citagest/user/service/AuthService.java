package es.citagest.user.service;

import es.citagest.user.dto.UserDTO;
import es.citagest.user.entity.UserEntity;

public interface AuthService {
    UserDTO login(String username, String password);
}
