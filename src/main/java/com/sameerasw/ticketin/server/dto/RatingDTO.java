package com.sameerasw.ticketin.server.dto;

public class RatingDTO {
    private Long id;
    private int rating;
    private Long eventItemId;
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getEventItemId() {
        return eventItemId;
    }

    public void setEventItemId(Long eventItemId) {
        this.eventItemId = eventItemId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}