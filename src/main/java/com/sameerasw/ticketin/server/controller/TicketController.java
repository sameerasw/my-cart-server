package com.sameerasw.ticketin.server.controller;


import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable Long eventId) {
        List<Ticket> tickets = ticketService.getTicketsByEventId(eventId);
        return tickets != null ? new ResponseEntity<>(tickets, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return tickets != null ? new ResponseEntity<>(tickets, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}