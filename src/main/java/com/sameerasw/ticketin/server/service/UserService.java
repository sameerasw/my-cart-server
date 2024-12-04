// src/main/java/com/sameerasw/ticketin/server/service/UserService.java
package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;

    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Vendor findVendorByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email) != null || vendorRepository.findByEmail(email) != null;
    }

    public String generateSimpleToken(Long userId) {
        // Simple token generation (not secure, just for demo purposes)
        return Base64.getEncoder().encodeToString((userId + ":" + System.currentTimeMillis()).getBytes());
    }
}