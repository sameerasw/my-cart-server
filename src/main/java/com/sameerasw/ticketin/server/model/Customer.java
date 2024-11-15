package com.sameerasw.ticketin.server.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User {
    private int ticketRetrievalRate;

    // Constructors, getters, and setters
    public Customer() {
    }

    public Customer(String name, String email) {
        super(name, email, false);
        this.ticketRetrievalRate = 0;
    }

    // simulating a customer
    public Customer(String name, String email, int ticketRetrievalRate) {
        super(name, email, true);
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                '}';
    }
}