package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    protected String email;
    @JsonIgnore
    protected String password;
    protected String name;
    protected String imageUrl;
    protected String phone;
    @Column(name = "date_of_birth")
    protected LocalDate dateOfBirth;
    @Column(name = "contact_address")
    protected String contactAddress;
    protected Boolean isDeleted = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
