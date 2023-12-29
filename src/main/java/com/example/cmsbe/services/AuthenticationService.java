package com.example.cmsbe.services;

import com.example.cmsbe.config.JwtService;
import com.example.cmsbe.models.AuthenticationResponse;
import com.example.cmsbe.models.AuthenticationRequest;
import com.example.cmsbe.models.RegisterRequest;
import com.example.cmsbe.models.User;
import com.example.cmsbe.repositories.UserRepository;
import com.example.cmsbe.services.interfaces.IAuthenticationService;
import com.example.cmsbe.utils.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final AuthenticationUtil authenticationUtil;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(authenticationUtil.encodePassword(request.getPassword()))
                .name(request.getName())
                .imageUrl(request.getImageUrl())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .contactAddress(request.getContactAddress())
                .userType(request.getUserType())
//                .employeeCode(request.getEmployeeCode())
                .salary(request.getSalary())
                .startedWorkingDate(request.getStartedWorkingDate())
                .employeeType(request.getEmployeeType())
                .build();
        User savedUser = userRepository.save(user);
        savedUser.generateEmployeeCode();
        userRepository.save(savedUser);

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, null, user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository
                    .findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("This account is not found"));
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("Authenticate successfully")
                    .user(user)
                    .build();
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("This account is not found", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("User not found or Incorrect password", e);
        }
    }
}
