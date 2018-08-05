package com.shaharbar.employeedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Import(SpringDataRestConfiguration.class)
@SpringBootApplication
public class EmployeeDemoApplication {
	
	@Autowired 
	EmployeeRepository employeeRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeDemoApplication.class, args);
	}
}
