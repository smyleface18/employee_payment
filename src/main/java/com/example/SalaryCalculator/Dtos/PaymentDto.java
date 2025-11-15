package com.example.SalaryCalculator.Dtos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String id; // String u ObjectId

    private Long hour;
    private Double money;
}
