package com.example.springmvcdemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private SecretKey JWT_SECRET; // Declare JWT_SECRET as a SecretKey

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        this.JWT_SECRET = getJwtSecretKey(); // Initialize the secret key
        
        // Get the token from the x-auth-token header
        String token = request.getHeader("x-auth-token");
        System.out.println("Token received: " + token); // Debug log
        
        if (token != null) {
            try {
                // Parse the token and extract claims
                Claims claims = Jwts.parser()
                        .setSigningKey(JWT_SECRET) // Use SecretKey for verification
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject(); // Extract user ID from token
                System.out.println("User ID from token: " + userId); // Debug log
                System.out.println("Working Fine !");

                if (userId != null) {
                    // Set the authentication in the security context
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                
            } catch (Exception e) {
                System.err.println("Token parsing error: " + e.getMessage()); // Debug log
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // Early return if the token is invalid
            }
        }
    
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }

    private SecretKey getJwtSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode("6a12a9975e447e0f01812f80bd78c2341bce0c5ddb985139a23b88cd166a3b9a7da2bcb76689dabd2142149457c5c730636fb36c71cd5bfbce696a0ba7934f1f");
        return new SecretKeySpec(keyBytes, "HmacSHA512"); // Adjust based on your hashing algorithm
    }
}
