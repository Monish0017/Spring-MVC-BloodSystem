package com.example.springmvcdemo.repository;

import com.example.springmvcdemo.model.Requests;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestsRepository extends MongoRepository<Requests, String> {
}
