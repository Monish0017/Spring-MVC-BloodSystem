package com.example.springmvcdemo.service;

import com.example.springmvcdemo.model.User;

public interface UserService {
    User save(User user);
    User findByPhone(String phone);
    boolean existsByPhone(String phone);
    String hashPassword(String password);
    boolean checkPassword(String rawPassword, String hashedPassword);
    User findById(String id);
}
