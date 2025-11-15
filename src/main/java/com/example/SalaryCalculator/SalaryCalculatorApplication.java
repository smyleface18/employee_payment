package com.example.SalaryCalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
public class SalaryCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryCalculatorApplication.class, args);
	}

}
