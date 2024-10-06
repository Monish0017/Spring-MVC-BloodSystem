package com.example.springmvcdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.springmvcdemo.repository")
public class SpringMvcDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMvcDemoApplication.class, args);
    }

    // Initial API endpoint set to /api
    @RestController
    public static class ApiController {
        
        @GetMapping("/api")
        public String healthCheck() {
            return "API is up and running!";
        }
    }
}
