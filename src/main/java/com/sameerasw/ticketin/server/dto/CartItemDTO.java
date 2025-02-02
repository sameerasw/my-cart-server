package com.sameerasw.ticketin.server.dto;

public class CartItemDTO {
    private Long id;
    private Long customerId;
    private Long eventItemId;
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEventItemId() {
        return eventItemId;
    }

    public void setEventItemId(Long eventItemId) {
        this.eventItemId = eventItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}