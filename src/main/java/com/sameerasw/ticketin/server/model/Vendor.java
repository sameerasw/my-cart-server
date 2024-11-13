package com.sameerasw.ticketin.server.model;

import jakarta.persistence.Entity;

@Entity
public class Vendor extends User implements IVendor {
    private int maxTicketPoolSize;
    private int ticketReleaseRate;

    // Constructors, getters, and setters
    public Vendor() {
    }

    public Vendor(String name, int maxTicketPoolSize, int ticketReleaseRate) {
        super(name);
        this.maxTicketPoolSize = maxTicketPoolSize;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public int getMaxTicketPoolSize() {
        return maxTicketPoolSize;
    }

    @Override
    public void setMaxTicketPoolSize(int maxTicketPoolSize) {
        this.maxTicketPoolSize = maxTicketPoolSize;
    }

    @Override
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    @Override
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", maxTicketPoolSize=" + maxTicketPoolSize +
                ", ticketReleaseRate=" + ticketReleaseRate +
                '}';
    }
}