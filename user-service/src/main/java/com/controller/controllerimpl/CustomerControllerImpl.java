package com.controller.controllerimpl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.controller.CustomerController;
import com.model.Customer;
import com.service.CustomerService;



@RestController
public class CustomerControllerImpl implements CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	private static final Logger logger = LogManager.getLogger(CustomerControllerImpl.class);

	@Override
	public ResponseEntity<String> registerUser(Customer customer) {
		try {
			
			return customerService.registerUser(customer);
			
		} catch (Exception e) {
			logger.error("An Error Occured",e);
		}
		
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
