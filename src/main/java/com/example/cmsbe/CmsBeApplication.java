package com.example.cmsbe;

import com.example.cmsbe.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
