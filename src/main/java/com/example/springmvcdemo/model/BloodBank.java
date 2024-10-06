// ------- BloodBank Model -------
package com.example.springmvcdemo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "BloodBanks")
public class BloodBank {
    @Id
    private String id;

    private String name;
    private String hospital;
    private String contactPerson;
    private String category;
    private String website;
    private String phone;
    private String email;
    private String password;
    private String state;
    private String district;
    private String address;
    private double latitude;
    private double longitude;

    @DBRef
    private List<Requests> requests;

    @DBRef
    private List<Donations> donations;

    private Map<String, Integer> stock;

    
}