package com.example.cmsbe.services;

import com.example.cmsbe.models.Employee;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
//import com.example.cmsbe.repositories.EmployeeRepository;
import com.example.cmsbe.repositories.EmployeeRepository;
import com.example.cmsbe.repositories.UserRepository;
import com.example.cmsbe.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
//    private final UserRepository<User> userRepository;
    private final EmployeeRepository employeeRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>();
//        return userRepository.findByUserType(UserType.EMPLOYEE);
    }

    @Override
    public PaginationDTO<User> getAllEmployees(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        var result = userRepository.findByUserType(UserType.EMPLOYEE, pageable);
//        return new PaginationDTO<>(result);
        return new PaginationDTO<>();
    }

    @Override
    public PaginationDTO<User> searchEmployees(String name, String email, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        var result = userRepository.findByUserTypeAndNameContainingAndEmailContaining(UserType.EMPLOYEE, name, email, pageable);
//        return new PaginationDTO<>(result);
        return new PaginationDTO<>();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.generateEmployeeCode();
        return employeeRepository.save(savedEmployee);
    }

    @Override
    public User getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Override
    public Employee updateEmployee(
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
    ) {
        Employee employee = (Employee)
                employeeRepository.findById(id)
//                employeeRepository.findByUserType(UserType.EMPLOYEE, id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        if (email != null) employee.setEmail(email);
        if (name != null) employee.setName(name);
        if (image != null) {
            var imageUrl = cloudinaryService.uploadFile(image, "cms/users/employees/", name).get("url").toString();
            employee.setImageUrl(imageUrl);
        }
        if (phone != null) employee.setPhone(phone);
        if (dateOfBirth != null) employee.setDateOfBirth(dateOfBirth);
        if (contactAddress != null) employee.setContactAddress(contactAddress);
        if (salary != null) employee.setSalary(salary);
        if (startedWorkingDate != null) employee.setStartedWorkingDate(startedWorkingDate);
        if (employeeType != null) employee.setEmployeeType(employeeType);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
