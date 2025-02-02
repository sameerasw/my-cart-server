// src/main/java/com/sameerasw/ticketin/server/model/Rating.java
package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @ManyToOne
    private EventItem eventItem;

    @ManyToOne
    private Customer customer;

    public Rating() {
    }

    public Rating(int rating, EventItem eventItem, Customer customer) {
        this.rating = rating;
        this.eventItem = eventItem;
        this.customer = customer;
    }

    public Rating(int rating, EventItem eventItem) {
        this.rating = rating;
        this.eventItem = eventItem;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventItem getEventItem() {
        return eventItem;
    }

    public Customer getCustomer() {
        return customer;
    }
}