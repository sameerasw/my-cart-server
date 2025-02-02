package com.sameerasw.ticketin.server.model;

import com.sameerasw.ticketin.server.dto.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Rating {

    @Id
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

        public int getRating() {
            return rating;
        }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public EventItem getEventItem() {
        return eventItem;
    }

    public Customer getCustomer() {
        return customer;
    }
}
