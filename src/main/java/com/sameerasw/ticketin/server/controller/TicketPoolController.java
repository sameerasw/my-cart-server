package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketpools")
public class TicketPoolController {
    @Autowired
    private TicketPoolService ticketPoolService;

    @PostMapping
    public ResponseEntity<TicketPool> createTicketPool(@RequestBody TicketPool ticketPool) {
        return new ResponseEntity<>(ticketPoolService.createTicketPool(ticketPool), HttpStatus.CREATED);
    }

    @GetMapping("/{eventItemId}") // Changed path variable name
    public ResponseEntity<TicketPool> getTicketPoolByEventItemId(@PathVariable Long eventItemId) { // Changed method name
        TicketPool ticketPool = ticketPoolService.getTicketPoolByEventItemId(eventItemId); // Changed method call
        return ticketPool != null ? new ResponseEntity<>(ticketPool, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}