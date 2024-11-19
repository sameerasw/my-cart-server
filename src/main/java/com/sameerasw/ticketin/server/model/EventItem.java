package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class EventItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String eventName;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private double ticketPrice;
    private boolean isSimulated;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToOne
    @JoinColumn(name = "pool_id")
    private TicketPool ticketPool;

    // Constructors, getters, and setters
    public EventItem() {
    }

    public EventItem(String eventName, String eventLocation, String eventDate, String eventTime, double ticketPrice, Vendor vendor) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.ticketPrice = ticketPrice;
        this.vendor = vendor;
        this.isSimulated = false;
    }

    public EventItem(String eventName, Vendor vendor, boolean isSimulated) {
        this.eventName = eventName;
        this.vendor = vendor;
        this.isSimulated = isSimulated;
    }

    public void createTicketPool(int maxPoolSize) {
        this.ticketPool = new TicketPool(maxPoolSize, this);
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public double getTicketPrice() {
        return this.ticketPrice;
    }

    public Long getId() {
        return this.eventId;
    }

    public String getEventName() {
        return this.eventName;
    }

    public Long getEventId() {
        return this.eventId;
    }

    @Override
    public String toString() {
        return "EventItem{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", isSimulated=" + isSimulated +
                ", vendor=" + vendor.getName() +
                ", availableTickets=" + ticketPool.getAvailableTickets() +
                '}';
    }

    public String getName() {
        return this.eventName;
    }


    // ... getters and setters ...
}