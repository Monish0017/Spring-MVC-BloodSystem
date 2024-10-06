package com.example.springmvcdemo.repository;

import com.example.springmvcdemo.model.Donations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DonationsRepository extends MongoRepository<Donations, String> {
}