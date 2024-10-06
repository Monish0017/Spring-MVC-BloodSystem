package com.example.springmvcdemo.service;

import com.example.springmvcdemo.model.BloodBank;
import com.example.springmvcdemo.repository.BloodBankRepository; // Make sure to create a BloodBankRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BloodBankServiceImpl implements BloodBankService {

    @Autowired
    private BloodBankRepository bloodBankRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // For password encoding

    @Override
    public BloodBank save(BloodBank bloodBank) {
        return bloodBankRepository.save(bloodBank);
    }

    @Override
    public BloodBank findByPhone(String phone) {
        return bloodBankRepository.findByPhone(phone);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return bloodBankRepository.existsByPhone(phone);
    }

    @Override
    public BloodBank findById(String id) {
        return bloodBankRepository.findById(id).orElse(null);
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
