package com.service;

import org.springframework.http.ResponseEntity;

import com.model.Customer;


public interface CustomerService {
	
	ResponseEntity<String> registerUser(Customer customer);
	
}
