package com.example.SalaryCalculator.entities;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "payment_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRecord {

    @MongoId
    private String id; // String u ObjectId

    private Long hour;
    private Double money;

    @DBRef
    private Employee employee; // referencia al empleado

    public PaymentRecord(String id, Long hour, Double money) {
        this.id = id;
        this.hour = hour;
        this.money = money;
    }
}
