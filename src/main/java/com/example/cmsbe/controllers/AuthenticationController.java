package com.example.cmsbe.controllers;

import com.example.cmsbe.models.AuthenticationResponse;
import com.example.cmsbe.models.AuthenticationRequest;
import com.example.cmsbe.models.RegisterRequest;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import com.example.cmsbe.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
            ) {
            return ResponseEntity.ok(authService.register(request));


    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
            ) {
        try {
        return ResponseEntity.ok(authService.authenticate(request));
        } catch (UsernameNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("This account is not found").build());
        } catch (BadCredentialsException e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("User not found or Incorrect password").build());
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create-mock-data")
    public void createMockData() {
        System.out.println("CREATE MOCK DATA");
        authService.register(
                new RegisterRequest(
                        "employee1@gmail.com",
                        "employee1",
                        "Employee 1",
                        null,
                        "0123456789",
                        LocalDate.of(2000, 12, 30),
                        "Contact address 1",
                        UserType.EMPLOYEE,
                        "1001",
                        1000.0,
                         LocalDate.now(),
                        EmployeeType.SALE
                )
        );
        authService.register(
                new RegisterRequest(
                        "employee2@gmail.com",
                        "employee2",
                        "Employee 2",
                        null,
                        "0123456789",
                        LocalDate.of(2000, 12, 30),
                        "Contact address 2",
                        UserType.EMPLOYEE,
                        "1002",
                        1000.0,
                        LocalDate.now(),
                        EmployeeType.WAREHOUSE
                )
        );
        authService.register(
                new RegisterRequest(
                        "manager@gmail.com",
                        "manager",
                        "Manager",
                        null,
                        "0123456789",
                        LocalDate.of(2000, 12, 30),
                        "Manager's Contact address",
                        UserType.MANAGER,
                        null,
                        null,
                        LocalDate.now(),
                        null
                )
        );
    }
}
