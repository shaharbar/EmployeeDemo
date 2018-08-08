package com.shaharbar.employeedemo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaharbar.employeedemo.repository.EmployeeRepository;
import com.shaharbar.employeedemo.repository.dao.Employee;
import com.shaharbar.employeedemo.repository.dao.StatusEnum;

@RestController
public class EmployeeController {

	final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeRepository employeeRepo;

	@RequestMapping(value = "/greeting", method = RequestMethod.DELETE)
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "Hello " + name;
	}

	@RequestMapping(value = "/employeeDemo/{id}", method = RequestMethod.DELETE)
	public Employee deactivateEmployee(@PathVariable("id") String id) {

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

		return employee;
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
