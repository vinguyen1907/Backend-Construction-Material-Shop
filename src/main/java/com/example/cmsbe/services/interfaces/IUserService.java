package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface IUserService {
    PaginationDTO<User> getAllEmployees(int page, int size);
    PaginationDTO<User> searchEmployees(String name, String email, int page, int size);
    User createEmployee(User user);
    User getEmployeeById(Integer employeeId);
    User updateEmployee(
            Integer id,
            String email,
            String name,
            MultipartFile image,
            String phone,
            LocalDate dateOfBirth,
            String contactAddress,
            Double salary,
            LocalDate startedWorkingDate,
            EmployeeType employeeType
    );
    void deleteEmployee(Integer employeeId);
}
