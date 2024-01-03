package com.example.cmsbe.repositories;

import com.example.cmsbe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {
    Optional<User> findByEmail(String email);
//    List<User> findByUserType(UserType userType);
//    Page<User> findByUserType(UserType userType, Pageable pageable);
//    Page<User> findByUserTypeAndNameContainingAndEmailContaining(UserType userType, String name, String email, Pageable pageable);
}
