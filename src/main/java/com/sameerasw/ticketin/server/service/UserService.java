package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

// src/main/java/com/sameerasw/ticketin/server/service/UserService.java
@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public String generateSimpleToken(Long userId) {
        // Simple token generation (not secure, just for demo purposes)
        return Base64.getEncoder().encodeToString((userId + ":" + System.currentTimeMillis()).getBytes());
    }
}