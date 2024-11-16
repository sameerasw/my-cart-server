package com.sameerasw.ticketin.server.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poolId;
    private int maxPoolSize;
    private int availableTickets;

    @ManyToOne
    @JoinColumn(name = "event_item_id")
    private EventItem eventItem;

    @OneToMany(mappedBy = "ticketPool", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    // Constructors, getters, and setters
    public TicketPool() {}

    public TicketPool(int maxPoolSize, EventItem eventItem) {
        this.maxPoolSize = maxPoolSize;
        this.eventItem = eventItem;
        this.availableTickets = 0;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public Long getPoolId() {
        return poolId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}