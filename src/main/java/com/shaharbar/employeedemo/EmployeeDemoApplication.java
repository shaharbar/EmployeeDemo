package com.shaharbar.employeedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;

// @Import(SpringDataRestConfiguration.class)
@SpringBootApplication
public class EmployeeDemoApplication {
	
	@Autowired
	private EmployeeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDemoApplication.class, args);
	}
}
