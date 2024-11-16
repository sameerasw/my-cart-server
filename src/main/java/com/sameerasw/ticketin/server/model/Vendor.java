package com.sameerasw.ticketin.server.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Vendor extends User {
    private int ticketReleaseRate;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventItem> events;

    // Constructors, getters, and setters
    public Vendor() {}

    public Vendor(String name, String email, int ticketReleaseRate) {
        super(name, email, true); // Assume simulated vendors
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public long getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    // ... getters and setters ...
}