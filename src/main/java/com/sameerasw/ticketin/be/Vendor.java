package com.sameerasw.ticketin.be;

import jakarta.persistence.*;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int maxTicketPoolSize;
    private int ticketReleaseRate;

    // Constructors, getters, and setters
}