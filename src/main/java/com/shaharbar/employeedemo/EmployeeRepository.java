package com.shaharbar.employeedemo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

	public List<Employee> findByFirstName(String firstName);

	public List<Employee> findByLastName(String lastName);
	
	public List<Employee> findByStatus(StatusEnum status);

}
