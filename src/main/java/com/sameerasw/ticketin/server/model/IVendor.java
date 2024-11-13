package com.sameerasw.ticketin.server.model;

public interface IVendor {
    int getMaxTicketPoolSize();
    void setMaxTicketPoolSize(int maxTicketPoolSize);
    int getTicketReleaseRate();
    void setTicketReleaseRate(int ticketReleaseRate);
}