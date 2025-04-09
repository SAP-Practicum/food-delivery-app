package org.example.food_delivery_app.Controller;

import jakarta.validation.Valid;
import org.example.food_delivery_app.model.*;
import org.example.food_delivery_app.repository.CustomerRepository;
import org.example.food_delivery_app.repository.DeliveryRepository;
import org.example.food_delivery_app.repository.EmployeeRepository;
import org.example.food_delivery_app.repository.UserRepository;
import org.example.food_delivery_app.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final DeliveryRepository deliveryRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, CustomerRepository customerRepository, EmployeeRepository employeeRepository, DeliveryRepository deliveryRepository, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.deliveryRepository = deliveryRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if (userRepository.findByEmail(loginRequest.getEmail()).isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email does not exist.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);

        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@Valid @RequestBody Customer customer){

       if(userRepository.existsByEmail(customer.getEmail())){
           return ResponseEntity.badRequest().body("Error: Email already exists.");
       }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.CUSTOMER);

        customerRepository.save(customer);
        return ResponseEntity.ok("Customer registered successfully");
    }

    @PostMapping("/register/employee")
    public ResponseEntity<String> registerEmployee(@Valid @RequestBody Employee employee){

        if(userRepository.existsByEmail(employee.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email already exists.");
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Role.EMPLOYEE);
        employeeRepository.save(employee);
        return ResponseEntity.ok("Employee registered successfully");
    }

    @PostMapping("/register/delivery")
    public ResponseEntity<String> registerDelivery(@Valid @RequestBody Delivery delivery){
        if(userRepository.existsByEmail(delivery.getEmail())){
            return ResponseEntity.badRequest().body("Error: Email already exists.");
        }
        delivery.setPassword(passwordEncoder.encode(delivery.getPassword()));
        delivery.setRole(Role.DELIVERY);
        deliveryRepository.save(delivery);
        return ResponseEntity.ok("Delivery registered successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out. Please delete token on client side.");
    }
}
