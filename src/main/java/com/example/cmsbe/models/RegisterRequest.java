package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String imageUrl;
    private String phone;
    private LocalDate dateOfBirth;
    private String contactAddress;
    private UserType userType;
    private String employeeCode;
    private Double salary;
    private Date startedWorkingDate;
    private EmployeeType employeeType;
}
