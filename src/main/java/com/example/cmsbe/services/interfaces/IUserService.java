package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    List<User> getAllEmployees();
    PaginationDTO<User> getAllEmployees(int page, int size);
    PaginationDTO<User> searchEmployees(String name, String email, int page, int size);
    User createEmployee(User user);
    User getEmployeeById(Integer employeeId);
    User getUserByEmail(String email);
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
