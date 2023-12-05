package com.example.cmsbe;

import com.example.cmsbe.models.RegisterRequest;
import com.example.cmsbe.models.User;
import com.example.cmsbe.models.enums.EmployeeType;
import com.example.cmsbe.models.enums.UserType;
import com.example.cmsbe.services.AuthenticationService;
import com.example.cmsbe.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class CmsBeApplication {
	private final AuthenticationService authService;

	public static void main(String[] args) {
		SpringApplication.run(CmsBeApplication.class, args);
	}

	@GetMapping("/")
	public String init( ) {
		return "Hello world";
	}

}
