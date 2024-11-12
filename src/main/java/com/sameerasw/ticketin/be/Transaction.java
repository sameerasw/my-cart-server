package com.sameerasw.ticketin.be;

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
}