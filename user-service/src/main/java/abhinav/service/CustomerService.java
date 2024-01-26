package abhinav.service;

import org.springframework.http.ResponseEntity;

import abhinav.model.Customer;


public interface CustomerService {
	
	ResponseEntity<String> registerUser(Customer customer);
	
}
