package org.example.food_delivery_app.Service;


import org.example.food_delivery_app.model.*;
import org.example.food_delivery_app.repository.UserRepository;
import org.example.food_delivery_app.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void UserService_getByEmail_Customer(){

        String email = "test@test.com";
        User customer = new Customer();
        customer.setUsername(email);
        customer.setEmail(email);
        customer.setPassword("password");
        customer.setRole(Role.CUSTOMER);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

        UserDetails userDetails = userDetailsService.loadUserByUsername(customer.getEmail());

        assertEquals(email,userDetails.getUsername());
        assertEquals("ROLE_"+Role.CUSTOMER,userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void UserService_getByEmail_Employee(){
        String email = "admin@example.com";
        User employee = new Employee();
        employee.setUsername(email);
        employee.setPassword("password");
        employee.setRole(Role.EMPLOYEE);
        employee.setUsername(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(employee));

        UserDetails userDetails = userDetailsService.loadUserByUsername(employee.getUsername());
        assertEquals(email,userDetails.getUsername());
        assertEquals("ROLE_"+Role.EMPLOYEE,userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void UserService_getByEmail_Delivery(){
        String email = "delivery@example.com";
        User delivery = new Delivery();
        delivery.setUsername(email);
        delivery.setPassword("password");
        delivery.setRole(Role.DELIVERY);


        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(delivery));

        UserDetails userDetails = userDetailsService.loadUserByUsername(delivery.getUsername());
        assertEquals(email,userDetails.getUsername());
        assertEquals("ROLE_"+Role.DELIVERY, userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void UserService_getByEmail_NotFound(){
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );
        assertEquals("User Not Found with email: " +email, exception.getMessage());
    }
}
