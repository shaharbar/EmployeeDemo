package com.shaharbar.employeedemo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class EmployeeController {
	
	final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired 
	EmployeeRepository employeeRepo;
	
	@RequestMapping (value = "/greeting", method = RequestMethod.DELETE)
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
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
	
	@RequestMapping(value = "/employeeDemo", method = RequestMethod.GET)
    public List<Employee> listActiveEmployees() {
 
		logger.info("Listing active employees...");

		return employeeRepo.findByStatus(StatusEnum.ACTIVE);
    }
}
