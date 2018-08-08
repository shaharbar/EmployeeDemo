package com.shaharbar.employeedemo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shaharbar.employeedemo.application.repository.EmployeeRepository;

// @Import(SpringDataRestConfiguration.class)
@SpringBootApplication
public class EmployeeDemoApplication {
	
	@Autowired 
	EmployeeRepository employeeRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeDemoApplication.class, args);
	}
}
