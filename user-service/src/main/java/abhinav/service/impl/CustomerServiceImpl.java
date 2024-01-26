package abhinav.service.impl;

import java.util.Date;
import java.util.Optional;

import abhinav.model.Customer;
import abhinav.repository.CustomerRepository;
import abhinav.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {
	
	Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public ResponseEntity<String> registerUser(Customer customer) {
		try {
			if (validateCustomer(customer)) {
				
				Optional<Customer> emailOptional = customerRepository.findByEmail(customer.getEmail());
				Optional<Customer> phoneNumberOptional = customerRepository.findByPhoneNumber(customer.getPhoneNumber());
				Optional<Customer> usernameOptional = customerRepository.findByUsername(customer.getUsername());
				
				if (emailOptional.isEmpty() && phoneNumberOptional.isEmpty() && usernameOptional.isEmpty()) {
					
					if (customer.getPhoneNumber().length()>14 || customer.getPhoneNumber().length()<10) {
						return new ResponseEntity<String>("{ \n\tmessage : phone number length is invalid\n}",HttpStatus.CONFLICT); 
					}
					
					customer.setCreatedDate(new Date());
					customer.setLastLogin(new Date());
					customerRepository.save(customer);
					
				} else if (emailOptional.isPresent()) {
					return new ResponseEntity<String>("{ \n\tmessage : email already exists \n}",HttpStatus.CONFLICT);
				} else if (phoneNumberOptional.isPresent()) {
					return new ResponseEntity<String>("{ \n\tmessage : phone number already exists \n}",HttpStatus.CONFLICT);
				} else if (usernameOptional.isPresent()) {
					return new ResponseEntity<String>("{ \n\tmessage : username already exists \n}",HttpStatus.CONFLICT);
				}
				
				return new ResponseEntity<String>("{ \n\tmessage : user added \n}",HttpStatus.CREATED);
				
			} else {
				return new ResponseEntity<>("{ \n\tmessage : some fields are missing \n}",HttpStatus.NO_CONTENT);
			}
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("{ \n\tmessage : data already exists \n}",HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("error in customer service impl ",e);
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Boolean validateCustomer(Customer customer) {
		if (customer.getUsername().isBlank()) return false;
		if (customer.getAddress().isBlank()) return false;
		if (customer.getEmail().isBlank()) return false;
		if (customer.getFirstName().isBlank()) return false; 
		if (customer.getLastName().isBlank())return false; 
		if (customer.getPassword().isBlank()) return false;
		if (customer.getPhoneNumber().isBlank()) return false;
		return true;
	}

}
