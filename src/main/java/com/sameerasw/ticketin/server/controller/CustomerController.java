package com.sameerasw.ticketin.server.controller;


import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PostMapping("/{customerId}/events/{eventId}/buy")
    public ResponseEntity<String> buyTicket(@PathVariable long customerId, @PathVariable long eventId) {
        Customer customer = customerService.createCustomer(new Customer("test", "test@test.com", 1));
        customerService.purchaseTicket(customer, eventId);
        return new ResponseEntity<>("Ticket purchased", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(true), HttpStatus.OK);
    }
}
