package com.sameerasw.ticketin.server.model;

import jakarta.persistence.Entity;

@Entity
public class Vendor extends User {
    private int maxTicketPoolSize;
    private int ticketReleaseRate;

    // Constructors, getters, and setters
    public Vendor() {
    }

    public Vendor(String name, String email) {
        super(name, email, false);
        this.maxTicketPoolSize = 0;
        this.ticketReleaseRate = 0;
    }

    public Vendor(String name, String email, int maxTicketPoolSize, int ticketReleaseRate) {
        super(name, email, true);
        this.maxTicketPoolSize = maxTicketPoolSize;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getMaxTicketPoolSize() {
        return maxTicketPoolSize;
    }

    public void setMaxTicketPoolSize(int maxTicketPoolSize) {
        this.maxTicketPoolSize = maxTicketPoolSize;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

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