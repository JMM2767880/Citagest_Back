package es.citagest.user.service.impl;

import es.citagest.user.dto.UserDTO;
import es.citagest.user.entity.UserEntity;
import es.citagest.user.mapper.UserMapper;
import es.citagest.user.repository.UserRepository;
import es.citagest.user.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO login(String username, String password) {
        UserEntity searchUser = userRepository.findByUsername(username)
                .orElse(null);

        if (searchUser == null) return null;

        boolean isLogin = encoder.matches(
                password,
                searchUser.getPasswordHash()
        );

        if (!isLogin) return null;

        return UserMapper.toDTO(searchUser);
    }
}
