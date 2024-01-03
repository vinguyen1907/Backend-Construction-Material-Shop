package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue(UserType.Values.EMPLOYEE)
public class Employee extends User {
    @Column(name = "employee_code")
    private String employeeCode;

    private Double salary;

    @Column(name = "started_working_date")
    private LocalDate startedWorkingDate;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Builder
    public Employee(
        Integer id,
        String email,
        String password,
        String name,
        String imageUrl,
        String phone,
        LocalDate dateOfBirth,
        String contactAddress,
        String employeeCode,
        Double salary,
        LocalDate startedWorkingDate,
        EmployeeType employeeType
    ) {
        super(
            id,
            email,
            password,
            name,
            imageUrl,
            phone,
            dateOfBirth,
            contactAddress,
            false
        );
        this.employeeCode = employeeCode;
        this.salary = salary;
        this.startedWorkingDate = startedWorkingDate;
        this.employeeType = employeeType;
    }

    public void generateEmployeeCode() {
        if (employeeType == null) {
            return;
        }

        switch (employeeType) {
            case WAREHOUSE:
                this.employeeCode = "EMPWH" + id;
                break;
            case SHIPPING:
                this.employeeCode = "EMPSP" + id;
                break;
            default:
                this.employeeCode = "EMPSL" + id;
        }
    }
}
