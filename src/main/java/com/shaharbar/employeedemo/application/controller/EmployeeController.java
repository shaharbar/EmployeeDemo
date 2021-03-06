package com.shaharbar.employeedemo.application.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shaharbar.employeedemo.application.repository.EmployeeRepository;
import com.shaharbar.employeedemo.application.repository.dao.Employee;
import com.shaharbar.employeedemo.application.repository.dao.StatusEnum;

@RestController
public class EmployeeController {
	
	final static String PASSWORD = "shaharbar";

	final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepository employeeRepo;

	@RequestMapping(value = "/employeeDemo/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deactivateEmployee(@PathVariable("id") String id, @RequestHeader("Authorization") String auth) {
		
		if (!auth.equals(PASSWORD))
			return new ResponseEntity<String>("ACCESS DENIED!!!", HttpStatus.UNAUTHORIZED);

		logger.info("About to dectivate Employee ID: " + id);

		Employee employee = null;

		Optional<Employee> employeeOptional = employeeRepo.findById(id);
		if (employeeOptional.isPresent()) {
			logger.info("Found Employee: " + id);
			employee = employeeOptional.get();
			employee.setStatus(StatusEnum.INACTIVE);

			employeeRepo.save(employee);
		} else {
			logger.error("Could not find Employee: " + id);
		}

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employeeDemo/activate/{id}", method = RequestMethod.PUT)
	public Employee activateEmployee(@PathVariable("id") String id) {

		logger.info("About to activate Employee ID: " + id);

		Employee employee = null;

		Optional<Employee> employeeOptional = employeeRepo.findById(id);
		if (employeeOptional.isPresent()) {
			logger.info("Found Employee: " + id);
			employee = employeeOptional.get();
			employee.setStatus(StatusEnum.ACTIVE);

			employeeRepo.save(employee);
		} else {
			logger.error("Could not find Employee: " + id);
		}

		return employee;
	}

	@RequestMapping(value = "/employeeDemo/{id}", method = RequestMethod.GET)
    public Employee getActiveEmployeeById(@PathVariable("id") String id) {
 
		logger.info("Getting emloyee: " + id);
		
		Optional<Employee> optionalEmployee = employeeRepo.findById(id);
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			if (employee.getStatus().equals(StatusEnum.ACTIVE))
				return employee;
			else {
				logger.info("Employee id=" + id + " is not ACTIVE!");
				throw new ResourceNotFoundException("Employee id=" + id + " is not ACTIVE!");
			}
		}
		else {
			logger.info("Employee id= " + id + " NOT FOUND!");	
			throw new ResourceNotFoundException();
		}			
    }
	
	@RequestMapping(value = "/employeeDemo/{id}", method = RequestMethod.PUT)
    public Employee updateEmployee(@PathVariable("id") String id, @RequestBody Employee updatedEmployee) {
		
		Optional<Employee> optionalEmployee = employeeRepo.findById(id);
		if (optionalEmployee.isPresent()) {
			updatedEmployee.setId(id);
			employeeRepo.save(updatedEmployee);
			return updatedEmployee;
		}
		else {
			logger.info("Employee id= " + id + " NOT FOUND!");	
			throw new ResourceNotFoundException();
		}			
	}
	
	@RequestMapping(value = "/employeeDemo", method = RequestMethod.POST)
    public Employee createEmployee(@RequestBody Employee employee) {
		employeeRepo.save(employee);
		return employee;
	}

	@RequestMapping(value = "/employeeDemo", method = RequestMethod.GET)
	public List<Employee> listActiveEmployees() {

		logger.info("Listing active employees...");

		return employeeRepo.findByStatus(StatusEnum.ACTIVE);
	}
}
