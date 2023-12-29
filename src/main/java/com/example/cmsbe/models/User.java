package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    @JsonIgnore
    private String password;
    private String name;
    private String imageUrl;
    private String phone;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "contact_address")
    private String contactAddress;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "employee_code")
    private String employeeCode;
    private Double salary;
    @Column(name = "started_working_date")
    private LocalDate startedWorkingDate;
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userType != null) {
            return List.of(new SimpleGrantedAuthority(userType.name()));
        }
        return List.of(new SimpleGrantedAuthority(UserType.EMPLOYEE.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
