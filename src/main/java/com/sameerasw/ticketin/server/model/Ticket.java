package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    private boolean isSimulated;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Vendor vendor; // Relationship to Vendor
    private double price;

    // Constructors, getters, and setters
    public Ticket() {
    }

    public Ticket(Vendor vendor, double price) {
        this.vendor = vendor;
        this.price = price;
        this.isSimulated = false;
    }

    public Ticket(Vendor vendor, double price, boolean isSimulated) {
        this.vendor = vendor;
        this.price = price;
        this.isSimulated = isSimulated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", vendor=" + vendor +
                ", price=" + price +
                '}';
    }
}