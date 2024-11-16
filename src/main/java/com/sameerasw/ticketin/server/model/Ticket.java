package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @ManyToOne
    private TicketPool ticketPool;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isSold = false;
    private boolean isSimulated;

    @ManyToOne
    private EventItem eventItem; // Link to Event
    @ManyToOne
    private Customer customer; // Link to Customer

    // Constructors, getters, and setters
    public Ticket() {}

    public Ticket(EventItem eventItem, boolean isSimulated) {
        this.eventItem = eventItem;
        this.isSimulated = isSimulated;
        this.ticketPool = eventItem.getTicketPool();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return this.id;
    }

    public double getTicketPrice() {
        return eventItem.getTicketPrice();
    }

    public boolean isAvailable() {
        return !isSold;
    }

    public void sellTicket() {
        isSold = true;
    }
    // ... getters and setters ...
}