package com.example.springmvcdemo.controller;

import com.example.springmvcdemo.model.BloodBank;
import com.example.springmvcdemo.service.BloodBankService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/bloodbank")
public class BloodBankController {

    private final BloodBankService bloodBankService;
    private final SecretKey JWT_SECRET;

    public BloodBankController(BloodBankService bloodBankService) {
        this.bloodBankService = bloodBankService;
        String base64Secret = "6a12a9975e447e0f01812f80bd78c2341bce0c5ddb985139a23b88cd166a3b9a7da2bcb76689dabd2142149457c5c730636fb36c71cd5bfbce696a0ba7934f1f";
        this.JWT_SECRET = new SecretKeySpec(Base64.getDecoder().decode(base64Secret), "HmacSHA512");
    }

    // Register Blood Bank
    @PostMapping("/register")
    public ResponseEntity<BloodBank> registerBloodBank(@RequestBody BloodBank bloodBank) {
        if (bloodBank == null || bloodBank.getPhone() == null || bloodBank.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if (bloodBankService.existsByPhone(bloodBank.getPhone())) {
            return ResponseEntity.badRequest().body(null);
        }

        // Initialize stock if it's null
        if (bloodBank.getStock() == null) {
            bloodBank.setStock(new HashMap<>()); // Initialize stock
        }

        // Hash the password
        bloodBank.setPassword(bloodBankService.hashPassword(bloodBank.getPassword()));
        BloodBank savedBloodBank = bloodBankService.save(bloodBank);

        return ResponseEntity.ok(savedBloodBank);
    }

    // Login Blood Bank
    @PostMapping("/login")
    public ResponseEntity<?> loginBloodBank(@RequestBody BloodBank loginRequest) {
        BloodBank existingBloodBank = bloodBankService.findByPhone(loginRequest.getPhone());
        if (existingBloodBank != null && bloodBankService.checkPassword(loginRequest.getPassword(), existingBloodBank.getPassword())) {
            String token = createJwtToken(existingBloodBank);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Wrong phone or password.");
    }

    // Update Stock
    @PutMapping("/updateStock")
    public ResponseEntity<?> updateStock(@RequestHeader("x-auth-token") String token, @RequestBody UpdateStockRequest request) {
        try {
            // Extract and validate the JWT token
            String bloodBankId = validateAndGetUserId(token);
            if (bloodBankId == null) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            BloodBank bloodBank = bloodBankService.findById(bloodBankId);
            if (bloodBank == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if stock is initialized
            if (bloodBank.getStock() == null) {
                bloodBank.setStock(new HashMap<>()); // Initialize stock if null
            }

            // Update the stock
            int currentStock = bloodBank.getStock().getOrDefault(request.getBloodGroup(), 0);
            bloodBank.getStock().put(request.getBloodGroup(), currentStock + request.getUnits());

            System.out.println("Working Fine !");
            bloodBankService.save(bloodBank); // Save the updated blood bank

            return ResponseEntity.ok().build();
        } catch (Exception err) {
            System.err.println(err);
            return ResponseEntity.status(500).build();
        }
    }

    // Delete Stock
    @DeleteMapping("/deleteStock")
    public ResponseEntity<?> deleteStock(@RequestHeader("x-auth-token") String token, @RequestBody UpdateStockRequest request) {
        try {
            // Extract and validate the JWT token
            String bloodBankId = validateAndGetUserId(token);
            if (bloodBankId == null) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            BloodBank bloodBank = bloodBankService.findById(bloodBankId);
            if (bloodBank == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if stock is initialized
            if (bloodBank.getStock() == null) {
                bloodBank.setStock(new HashMap<>()); // Initialize stock if null
            }

            // Check and update the stock
            int currentStock = bloodBank.getStock().getOrDefault(request.getBloodGroup(), 0);
            if (currentStock < request.getUnits()) {
                return ResponseEntity.status(404).body("Not enough blood");
            } else {
                bloodBank.getStock().put(request.getBloodGroup(), currentStock - request.getUnits());
                bloodBankService.save(bloodBank); // Save the updated blood bank
                return ResponseEntity.ok().build();
            }
        } catch (Exception err) {
            System.err.println(err);
            return ResponseEntity.status(500).build();
        }
    }


    // Get Stock
    @GetMapping("/getStock")
    public ResponseEntity<?> getStock(@RequestHeader("x-auth-token") String token) {
        try {
            // Extract and validate the JWT token
            String bloodBankId = validateAndGetUserId(token);
            if (bloodBankId == null) {
                return ResponseEntity.status(401).body("Unauthorized");
            }

            BloodBank bloodBank = bloodBankService.findById(bloodBankId);
            if (bloodBank == null) {
                return ResponseEntity.notFound().build();
            }

            // Check if stock is initialized
            if (bloodBank.getStock() == null) {
                bloodBank.setStock(new HashMap<>()); // Initialize stock if null
            }

            return ResponseEntity.ok(bloodBank.getStock());
        } catch (Exception err) {
            System.err.println(err);
            return ResponseEntity.status(500).build();
        }
    }

    private String validateAndGetUserId(String token) {
        if (token != null) {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(JWT_SECRET) // Use the SecretKey for verification
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                return claims.getSubject(); // Return user ID from the token
            } catch (Exception e) {
                System.err.println("JWT validation failed: " + e.getMessage());
            }
        }
        return null; // Return null if validation fails
    }

    private String createJwtToken(BloodBank bloodBank) {
        return Jwts.builder()
                .setSubject(bloodBank.getId()) // Use ID from BloodBank
                .claim("type", "bloodbank")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token expiration time (e.g., 1 day)
                .signWith(JWT_SECRET) // Use the secure key
                .compact();
    }

    // DTO for UpdateStock Request
    public static class UpdateStockRequest {
        private String bloodGroup; // Blood group to update
        private int units;         // Number of units to add or remove

        // Getters and Setters
        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public int getUnits() {
            return units;
        }

        public void setUnits(int units) {
            this.units = units;
        }
    }
}
