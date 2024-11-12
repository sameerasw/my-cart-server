package com.sameerasw.ticketin.be;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Vendor vendor; // Relationship to Vendor
    private double price;

    // Constructors, getters, and setters
}