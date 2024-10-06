package com.example.springmvcdemo.repository;

import com.example.springmvcdemo.model.Camp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampRepository extends MongoRepository<Camp, String> {
}