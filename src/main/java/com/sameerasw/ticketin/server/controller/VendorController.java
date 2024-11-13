package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.saveVendor(vendor);
    }

    // Add other controller methods for Vendor operations
}