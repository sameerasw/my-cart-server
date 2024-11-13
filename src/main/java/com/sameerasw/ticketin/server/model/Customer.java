package com.sameerasw.ticketin.server.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User implements ICustomer {
    private String email;
    private int ticketRetrievalRate;

    // Constructors, getters, and setters
    public Customer() {
    }

    public Customer(String name, String email, int ticketRetrievalRate) {
        super(name);
        this.email = email;
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }

    public void setTicketRetrievalRate(int ticketRetrievalRate) {
        this.ticketRetrievalRate = ticketRetrievalRate;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}