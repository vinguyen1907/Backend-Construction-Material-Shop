package com.example.cmsbe.repositories;

import com.example.cmsbe.models.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends UserRepository<Employee> {
////    List<Employee> findByUserType(UserType userType);
////    Page<Employee> findByUserType(UserType userType, Pageable pageable);
////    Page<Employee> findByUserTypeAndNameContainingAndEmailContaining(UserType userType, String name, String email, Pageable pageable);
}
