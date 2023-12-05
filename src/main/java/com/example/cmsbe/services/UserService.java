package com.example.cmsbe.services;

import com.example.cmsbe.models.User;
import com.example.cmsbe.repositories.UserRepository;
import com.example.cmsbe.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
