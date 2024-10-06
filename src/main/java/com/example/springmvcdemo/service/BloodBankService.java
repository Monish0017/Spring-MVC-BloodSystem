package com.example.springmvcdemo.service;

import com.example.springmvcdemo.model.BloodBank;

public interface BloodBankService {
    BloodBank save(BloodBank bloodBank);
    BloodBank findByPhone(String phone);
    boolean existsByPhone(String phone);
    BloodBank findById(String id);
    
    // New methods for password handling
    String hashPassword(String password);
    boolean checkPassword(String rawPassword, String hashedPassword);
}
