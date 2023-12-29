package com.example.cmsbe.controllers;

import com.example.cmsbe.config.Constants;
import com.example.cmsbe.dto.PaginationDTO;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import com.example.cmsbe.services.CloudinaryService;
import com.example.cmsbe.services.interfaces.IUserService;
import com.example.cmsbe.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users/employees")
@RequiredArgsConstructor
public class EmployeeController {
    final private IUserService userService;
    final private CloudinaryService cloudinaryService;
    final private AuthenticationUtil authenticationUtil;

    @GetMapping
    public ResponseEntity<PaginationDTO<User>> getAllEmployees(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(userService.getAllEmployees(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PaginationDTO<User>> searchEmployees(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ResponseEntity.ok(userService.searchEmployees(name, email, page, size));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<User> getEmployeeById(@PathVariable Integer employeeId) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.getEmployeeById(employeeId));
    }

    @PostMapping
    public ResponseEntity<User> createEmployee(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam MultipartFile image,
            @RequestParam String phone,
            @RequestParam LocalDate dateOfBirth,
            @RequestParam String contactAddress,
            @RequestParam Double salary,
            @RequestParam LocalDate startedWorkingDate,
            @RequestParam EmployeeType employeeType
    ) {
        var imageUrl = cloudinaryService.uploadFile(image, "cms/users/employees/", name);
        var user = new User(
                null,
                email,
                authenticationUtil.encodePassword(Constants.DEFAULT_EMPLOYEE_PASSWORD),
                name,
                imageUrl.get("url").toString(),
                phone,
                dateOfBirth,
                contactAddress,
                UserType.EMPLOYEE,
                null, // employeeCode
                salary,
                startedWorkingDate,
                employeeType,
                false
        );
        return ResponseEntity.ok(userService.createEmployee(user));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<User> updateEmployee(
            @PathVariable Integer employeeId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String contactAddress,
            @RequestParam(required = false) Double salary,
            @RequestParam(required = false) LocalDate startedWorkingDate,
            @RequestParam(required = false) EmployeeType employeeType
    ) throws EntityNotFoundException {
        return ResponseEntity.ok(userService.updateEmployee(
                employeeId,
                email,
                name,
                image,
                phone,
                dateOfBirth,
                contactAddress,
                salary,
                startedWorkingDate,
                employeeType
        ));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId) throws EntityNotFoundException {
        userService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
