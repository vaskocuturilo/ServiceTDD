package com.example.servicetdd.service;

import com.example.servicetdd.entity.UserEntity;
import com.example.servicetdd.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<UserEntity> getAllHandleUsers() {
        return userRepository.findAll();
    }
}
