package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer; // Relationship to Customer
    @ManyToOne
    private Ticket ticket; // Relationship to Ticket
    private double price;

    // Constructors, getters, and setters
    public Transaction() {
    }

    public Transaction(Customer customer, Ticket ticket, double price) {
        this.customer = customer;
        this.ticket = ticket;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", customer=" + customer +
                ", ticket=" + ticket +
                ", price=" + price +
                '}';
    }
}