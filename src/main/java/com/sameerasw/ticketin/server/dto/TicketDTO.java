package com.sameerasw.ticketin.server.dto;

public class TicketDTO {
    private String eventName;
    private String ticketId;
    private String imageUrl;
    private String dateTime;
    private String eventId;

    public TicketDTO() {
    }

    public TicketDTO(String eventName, String ticketId, String imageUrl, String dateTime, String eventId) {
        this.eventName = eventName;
        this.ticketId = ticketId;
        this.imageUrl = imageUrl;
        this.dateTime = dateTime;
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}