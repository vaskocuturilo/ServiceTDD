package com.example.servicetdd.service;

import com.example.servicetdd.entity.UserEntity;
import com.example.servicetdd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<UserEntity> getAllHandleUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity updateUser(Long id, UserEntity userEntity) {
        Optional<UserEntity> userData = userRepository.findById(id);
        if (!userData.isPresent()) {
            throw new IllegalStateException("User with id  = " + id + " not found");
        }
        UserEntity _userEntity = userData.get();
        _userEntity.setUsername(userEntity.getUsername());
        _userEntity.setPassword(userEntity.getPassword());

        return userRepository.save(_userEntity);
    }
}
