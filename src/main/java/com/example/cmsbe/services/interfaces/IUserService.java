package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.User;

import java.util.Optional;

public interface IUserService {
    void saveUser(User user);

    Optional<User> findByEmail(String email);
}
