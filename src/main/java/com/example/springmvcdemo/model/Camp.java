
// ------- Camp Model -------
package com.example.springmvcdemo.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@Document(collection = "Camps")
public class Camp {
    @Id
    private String id;

    private String name;
    private String date;
    private String address;
    private String state;
    private String district;

    @DBRef
    private BloodBank bank;

    private String organizer;
    private String contact;
    private String startTime;
    private String endTime;

    @DBRef
    private List<User> donors;
}
