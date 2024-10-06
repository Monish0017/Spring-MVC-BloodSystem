package com.example.springmvcdemo.repository;

import com.example.springmvcdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByPhone(String phone);
    boolean existsByPhone(String phone);  // Add this line
}
