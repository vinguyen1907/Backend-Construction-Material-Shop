package com.example.cmsbe.models;

import com.example.cmsbe.models.enums.UserType;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;

@Entity
@DiscriminatorValue(UserType.Values.MANAGER)
public class Manager extends User {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserType.MANAGER.name()));
    }
}
