package com.example.cmsbe;

import com.example.cmsbe.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.example.cmsbe")
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.example.cmsbe.repositories")
@EntityScan(basePackages = "com.example.cmsbe.models")
public class CmsBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsBeApplication.class, args);
	}
}
