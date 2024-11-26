package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.VendorDTO;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.MappingService;
import com.sameerasw.ticketin.server.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private MappingService mappingService;

    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        Vendor vendor = new Vendor(vendorDTO.getName(), vendorDTO.getEmail());
        return new ResponseEntity<>(mappingService.mapToVendorDTO(vendorService.createVendor(vendor)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors(true)
                .stream()
                .map(mappingService::mapToVendorDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
}