package com.example.springmvcdemo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private final String JWT_SECRET = "Spiderman"; // Use a secure secret

    // Method to extract claims from the token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(JWT_SECRET)
                   .parseClaimsJws(token)
                   .getBody();
    }

    // Method to validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to extract the subject (user ID) from the token
    public String getSubject(String token) {
        return extractClaims(token).getSubject();
    }

    // Method to extract user type from the token
    public String getUserType(String token) {
        return extractClaims(token).get("type", String.class);
    }
}
