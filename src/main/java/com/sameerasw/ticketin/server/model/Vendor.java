package com.sameerasw.ticketin.server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("VENDOR")
public class Vendor extends User {
    private int ticketReleaseRate;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventItem> events;

    public Vendor() {
    }

    public Vendor(String name, String email, String password) {
        super(name, email, password);
    }

    public Vendor(String name, String email, int ticketReleaseRate) {
        super(name, email, true);
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }
}