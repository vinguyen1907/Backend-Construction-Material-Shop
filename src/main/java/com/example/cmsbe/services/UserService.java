package com.example.cmsbe.services;

import com.example.cmsbe.models.User;
import com.example.cmsbe.models.dto.PaginationDTO;
import com.example.cmsbe.models.enums.EmployeeSearchBy;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import com.example.cmsbe.repositories.UserRepository;
import com.example.cmsbe.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<User> getAllEmployees() {
        return userRepository.findByUserType(UserType.EMPLOYEE);
    }

    @Override
    public PaginationDTO<User> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var result = userRepository.findByUserType(UserType.EMPLOYEE, pageable);
        return new PaginationDTO<>(result);
    }

    @Override
    public PaginationDTO<User> searchEmployees(String keyword, EmployeeSearchBy searchBy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return switch (searchBy) {
            case EMAIL ->
                    new PaginationDTO<>(userRepository.findByUserTypeAndEmailContaining(UserType.EMPLOYEE, keyword, pageable));
            case PHONE ->
                    new PaginationDTO<>(userRepository.findByUserTypeAndPhoneContaining(UserType.EMPLOYEE, keyword, pageable));
            default ->
                    new PaginationDTO<>(userRepository.findByUserTypeAndNameContaining(UserType.EMPLOYEE, keyword, pageable));
        };
    }

    @Override
    public User createEmployee(User user) {
        User savedUser = userRepository.save(user);
        savedUser.generateEmployeeCode();
        return userRepository.save(savedUser);
    }

    @Override
    public User getEmployeeById(Integer employeeId) {
        return userRepository.findById(employeeId).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Override
    public User updateEmployee(
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
        var employee = userRepository.findById(id)
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
        return userRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        userRepository.deleteById(employeeId);
    }
}
