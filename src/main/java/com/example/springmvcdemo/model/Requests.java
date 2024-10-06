// ------- Requests Model -------

package com.example.springmvcdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Document(collection = "Requests")
public class Requests {
    @Id
    private String id;               // Unique identifier for the request

    @DBRef
    private User user;               // Reference to the User making the request

    @DBRef
    private BloodBank bank;          // Reference to the associated Blood Bank

    private String name;             // Name of the requester (optional if user reference is used)
    private int age;                 // Age of the requester
    private String gender;           // Gender of the requester
    private String bloodGroup;       // Blood group of the requester
    private int units;               // Number of blood units requested
    private String date;             // Date of the request
    private String reason;           // Reason for the blood request
    private String status;           // Status of the request (e.g., pending, approved, rejected)
}
