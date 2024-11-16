package com.sameerasw.ticketin.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.concurrent.ConcurrentLinkedQueue;

@Entity
public class TicketPool {
    private int maxPoolSize;
    private ConcurrentLinkedQueue<Ticket> tickets = new ConcurrentLinkedQueue<>();
    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventItem eventItem;
    @Id
    private Long poolId;

    public EventItem getEvent() {
        return eventItem;
    }

    public void setEvent(EventItem eventItem) {
        this.eventItem = eventItem;
    }

    public TicketPool() {}

    public TicketPool(int maxPoolSize, EventItem eventItem) {
        this.maxPoolSize = maxPoolSize;
        this.eventItem = eventItem;
    }

    public synchronized Ticket removeTicket(Customer customer) {
        Ticket ticket = tickets.poll();
        if (ticket != null) {
            ticket.setCustomer(customer);
            return ticket;
        }
        return null;
    }

    public synchronized void addTicket(Ticket ticket) {
        if (tickets.size() < maxPoolSize) {
            tickets.offer(ticket);
        }
    }

    public void setPoolId(Long poolId) {
        this.poolId = poolId;
    }

    public Long getPoolId() {
        return poolId;
    }

    // ... getters and setters ...
}