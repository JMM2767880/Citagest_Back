package es.citagest.user.service.impl;

import es.citagest.user.dto.UserDTO;
import es.citagest.user.entity.UserEntity;
import es.citagest.user.mapper.UserMapper;
import es.citagest.user.repository.UserRepository;
import es.citagest.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        return userRepository.findById(id).map(UserMapper::toDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username){
        return userRepository.findByUsername(username).map(UserMapper::toDTO).orElse(null);
    }
}
