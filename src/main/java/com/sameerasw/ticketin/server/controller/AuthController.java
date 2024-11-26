// src/main/java/com/sameerasw/ticketin/server/controller/AuthController.java
package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.LoginRequest;
import com.sameerasw.ticketin.server.dto.LoginResponse;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Customer customer = userService.findByEmail(request.getEmail());

        if (customer == null || !customer.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }

        // Create simple token (not secure - just for demo)
        String token = userService.generateSimpleToken(customer.getId());

        LoginResponse response = new LoginResponse(
                token,
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                "CUSTOMER" // Set the user type
        );

        return ResponseEntity.ok(response);
    }
}