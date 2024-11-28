// src/main/java/com/sameerasw/ticketin/server/controller/AuthController.java
package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.LoginRequest;
import com.sameerasw.ticketin.server.dto.LoginResponse;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("CUSTOMER".equalsIgnoreCase(request.getUserType())) {
            Customer customer = userService.findCustomerByEmail(request.getEmail());
            if (customer == null || !customer.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            String token = userService.generateSimpleToken(customer.getId());
            LoginResponse response = new LoginResponse(token, customer.getId(), customer.getName(), customer.getEmail(), "CUSTOMER");
            return ResponseEntity.ok(response);
        } else if ("VENDOR".equalsIgnoreCase(request.getUserType())) {
            Vendor vendor = userService.findVendorByEmail(request.getEmail());
            if (vendor == null || !vendor.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
            String token = userService.generateSimpleToken(vendor.getId());
            LoginResponse response = new LoginResponse(token, vendor.getId(), vendor.getName(), vendor.getEmail(), "VENDOR");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Invalid user type");
        }
    }
}