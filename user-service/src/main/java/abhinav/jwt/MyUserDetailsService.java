package abhinav.jwt;

import java.util.ArrayList;
import java.util.Optional;

import abhinav.model.Customer;
import abhinav.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service loads user details from the database based on the provided username.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = userDao.findByUsername(username);
        if (customer.isPresent()) {
            Customer user = customer.get();
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
