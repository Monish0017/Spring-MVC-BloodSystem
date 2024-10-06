package com.example.springmvcdemo.config;

import com.example.springmvcdemo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/bloodbank/register", "/bloodbank/login", "/user/register", "/user/login").permitAll() // Allow these endpoints without authentication
                .requestMatchers("/bloodbank/updateStock" , "/bloodbank/deleteStock").authenticated() // Allow PUT request for updating stock without auth (if necessary)
                .anyRequest().authenticated() // All other requests need to be authenticated
            )
            .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
