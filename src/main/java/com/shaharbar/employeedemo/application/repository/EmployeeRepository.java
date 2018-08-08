package com.shaharbar.employeedemo.application.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shaharbar.employeedemo.application.repository.dao.Employee;
import com.shaharbar.employeedemo.application.repository.dao.StatusEnum;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	public List<Employee> findByFirstName(String firstName);

	public List<Employee> findByLastName(String lastName);
	
	public List<Employee> findByStatus(StatusEnum status);

}
