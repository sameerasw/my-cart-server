package com.sameerasw.ticketin.server.controller;


import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        return new ResponseEntity<>(vendorService.createVendor(vendor), HttpStatus.CREATED);
    }

    @PostMapping("/{vendorId}/events")
    public ResponseEntity<EventItem> createEvent(@PathVariable long vendorId, @RequestBody EventItem eventItem) {
        eventItem.setVendor(vendorService.createVendor(new Vendor("test", 1)));
        return new ResponseEntity<>(eventService.createEvent(eventItem), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        return new ResponseEntity<>(vendorService.getAllVendors(true), HttpStatus.OK);
    }

    @PostMapping("/{vendorId}/events/{eventId}/tickets")
    public ResponseEntity<String> releaseTickets(@PathVariable long vendorId, @PathVariable long eventId) {
        Vendor vendor = vendorService.createVendor(new Vendor("test", 1));
        vendorService.releaseTickets(vendor, eventId);
        return new ResponseEntity<>("Tickets released", HttpStatus.OK);
    }
}