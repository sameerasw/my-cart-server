package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // Add other controller methods for Customer operations
}