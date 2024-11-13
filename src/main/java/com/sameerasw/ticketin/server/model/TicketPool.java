package com.sameerasw.ticketin.server.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TicketPool {

    private ConcurrentHashMap<Long, ConcurrentLinkedQueue<Ticket>> ticketPools = new ConcurrentHashMap<>();

    public synchronized void addTicket(Ticket ticket) {
        long vendorId = ticket.getVendor().getId();
        ticketPools.computeIfAbsent(vendorId, k -> new ConcurrentLinkedQueue<>()).add(ticket);
    }

    public synchronized Ticket removeTicket(long vendorId) {
        ConcurrentLinkedQueue<Ticket> vendorPool = ticketPools.get(vendorId);
        if (vendorPool != null) {
            return vendorPool.poll();
        }
        return null;
    }

    // Other methods like getting available tickets for a vendor, etc.
}