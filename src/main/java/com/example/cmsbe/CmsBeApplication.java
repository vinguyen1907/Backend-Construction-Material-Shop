package com.example.cmsbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CmsBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBeApplication.class, args);
	}

	@GetMapping("")
	public String init( ) {
		return "Hello world";
	}
}
