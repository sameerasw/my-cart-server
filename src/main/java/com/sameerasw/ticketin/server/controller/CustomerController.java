package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.CustomerDTO;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.service.CustomerService;
import com.sameerasw.ticketin.server.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MappingService mappingService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName(), customerDTO.getEmail());
        return new ResponseEntity<>(mappingService.mapToCustomerDTO(customerService.createCustomer(customer)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers(true)
                .stream()
                .map(mappingService::mapToCustomerDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}