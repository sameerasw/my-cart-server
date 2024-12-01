package com.sameerasw.ticketin.server.model;

import com.sameerasw.ticketin.server.dto.TicketDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    private int ticketRetrievalRate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    // Constructors, getters, and setters
    public Customer() {
    }

    public Customer(String name, String email, String password) {
        super(name, email, password);
    }

    public Customer(String name, int ticketRetrievalRate) {
        super(name, true);
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public Customer(String name, String email) {
        super(name, email);
    }

    public int getTicketRetrievalRate() {
        return this.ticketRetrievalRate;
    }

    public List<TicketDTO> getTickets() {
        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getEventItem().getName(),
                        ticket.getTicketId().toString(),
                        ticket.getEventItem().getImageUrl(),
                        ticket.getEventItem().getDateTime()))
                .collect(Collectors.toList());
    }

    // ... getters and setters ...
}