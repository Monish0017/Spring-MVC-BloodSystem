// ------- User Model -------

package com.example.springmvcdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Users")
public class User {
    @Id
    private String id;               // Unique identifier for the user
    private String name;             // Name of the user
    private int age;                 // Age of the user
    private String gender;           // Gender of the user
    private String bloodGroup;       // Blood group of the user
    private String email;            // Email address of the user
    private String phone;            // Phone number of the user
    private String password;         // Password for the user account
    private String state;            // State of residence
    private String district;         // District of residence
    private String address;          // Full address of the user
}
