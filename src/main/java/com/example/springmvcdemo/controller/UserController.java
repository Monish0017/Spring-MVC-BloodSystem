package com.example.springmvcdemo.controller;

import com.example.springmvcdemo.model.User;
import com.example.springmvcdemo.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SecretKey JWT_SECRET; // Use a secure key

    public UserController(UserService userService) {
        this.userService = userService;
        // Generate a secure key for JWT
        this.JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user == null || user.getPhone() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (userService.existsByPhone(user.getPhone())) {
            return ResponseEntity.badRequest().body(null);
        }

        user.setPassword(userService.hashPassword(user.getPassword()));
        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        User existingUser = userService.findByPhone(loginRequest.getPhone());
        System.out.println("Login request: " + loginRequest);
    
        if (existingUser != null) {
            boolean passwordMatches = userService.checkPassword(loginRequest.getPassword(), existingUser.getPassword());
            System.out.println("Raw Password: " + loginRequest.getPassword());
            System.out.println("Hashed Password from DB: " + existingUser.getPassword());
            System.out.println("Password Matches: " + passwordMatches); // Debugging log
    
            if (passwordMatches) {
                String token = createJwtToken(existingUser);
                return ResponseEntity.ok(token);
            } else {
                System.out.println("Incorrect password for user: " + loginRequest.getPhone());
            }
        }
    
        return ResponseEntity.status(401).body("Wrong phone or password.");
    }
    
    

    private String createJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("type", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expiration: 24 hours
                .signWith(JWT_SECRET) // Use the secure key
                .compact();
    }
}
