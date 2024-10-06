package com.example.springmvcdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.springmvcdemo.model.BloodBank;

public interface BloodBankRepository extends MongoRepository<BloodBank, String> {
    // Custom query methods can be declared here if needed
    boolean existsByPhone(String phone);
    BloodBank findByPhone(String phone);
}
