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
    @JoinColumn(name = "event_item_id") // Changed to event_item_id
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

    public synchronized Ticket removeTicket(Customer customer) {
        if (availableTickets > 0) {
            Ticket ticket = tickets.remove(0); // Remove from the list
            if (ticket != null) {
                ticket.setCustomer(customer);
                ticket.sellTicket();
                availableTickets--;
                //remove from the pool table
                tickets.remove(ticket);
                return ticket;
            }
        }
        return null;
    }

    public void addTicket(Ticket ticket) {
        if (availableTickets < maxPoolSize) {
            tickets.add(ticket);
            availableTickets++;
        }
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

    public String getAvailableTickets() {
        return availableTickets + "/" + maxPoolSize;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }


    // ... getters and setters ...
}