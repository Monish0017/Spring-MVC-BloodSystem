// ------- Donations Model -------
package com.example.springmvcdemo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Document(collection = "Donations")
public class Donations {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private BloodBank bank;

    private int units;
    private String date;
    private String disease;
    private String status;
}