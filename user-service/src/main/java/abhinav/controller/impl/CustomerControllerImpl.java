package abhinav.controller.impl;


import abhinav.controller.CustomerController;
import abhinav.dto.LogInDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import abhinav.model.Customer;
import abhinav.service.CustomerService;



@RestController
public class CustomerControllerImpl implements CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	private static final Logger logger = LogManager.getLogger(CustomerControllerImpl.class);

	@Override
	public ResponseEntity<String> registerUser(Customer customer) {
			return customerService.registerUser(customer);
	}

	@Override
	public ResponseEntity<String> testingServer() {
		return new ResponseEntity<>("user service is working",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> registerUser(LogInDTO logInDTO) {
		return customerService.loginCustomer(logInDTO);
	}

}
