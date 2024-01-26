package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.Customer;


@RequestMapping("/customer")
public interface CustomerController {
	
	@PutMapping("/register")
	ResponseEntity<String> registerUser(@RequestBody Customer customer);

}
