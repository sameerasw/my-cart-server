package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    // Add other controller methods for Ticket operations
}