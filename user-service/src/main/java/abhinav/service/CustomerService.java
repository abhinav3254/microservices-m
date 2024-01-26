package abhinav.service;

import abhinav.dto.LogInDTO;
import org.springframework.http.ResponseEntity;

import abhinav.model.Customer;


public interface CustomerService {
	
	ResponseEntity<String> registerUser(Customer customer);

	ResponseEntity<String> loginCustomer(LogInDTO logInDTO);
	
}
