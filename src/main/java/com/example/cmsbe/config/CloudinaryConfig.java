package com.example.cmsbe.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dhwz3ojau",
                "api_key", "889348994254356",
                "api_secret", "R4MMJZhX7S57y3xMZm2nDxNGL5A"
        ));
    }
}
