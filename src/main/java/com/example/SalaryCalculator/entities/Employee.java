package com.example.SalaryCalculator.entities;



import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @MongoId
    private String id;

    private String identityNumber;
    private String name;
    private String lastname;
    private String email;
    private String img;

    @DBRef
    private Category category;
    @DBRef(lazy = false)
    private List<PaymentRecord> payments;


}
