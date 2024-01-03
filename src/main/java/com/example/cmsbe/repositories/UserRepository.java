package com.example.cmsbe.repositories;

import com.example.cmsbe.models.InventoryItem;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByUserType(UserType userType);
    Page<User> findByUserType(UserType userType, Pageable pageable);
    Page<User> findByUserTypeAndNameContainingAndEmailContaining(UserType userType, String name, String email, Pageable pageable);
}
