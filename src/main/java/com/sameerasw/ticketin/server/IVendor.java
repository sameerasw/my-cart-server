package com.sameerasw.ticketin.server;

public interface IVendor {
    int getMaxTicketPoolSize();
    void setMaxTicketPoolSize(int maxTicketPoolSize);
    int getTicketReleaseRate();
    void setTicketReleaseRate(int ticketReleaseRate);
}