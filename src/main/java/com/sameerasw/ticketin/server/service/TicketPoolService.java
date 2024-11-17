package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.TicketPoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TicketPoolService {
    private static final Logger logger = LoggerFactory.getLogger(TicketPoolService.class);

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    private final Lock lock = new ReentrantLock();

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    public Ticket removeTicket(TicketPool ticketPool, Customer customer) {
        lock.lock();
        try {
            if (ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().get(0);
                if (ticket != null) {
                    ticket.setCustomer(customer);
                    ticket.sellTicket();
                    return ticket;
                } else {
                    logger.info("No tickets available for the event, purchase denied. Try again later.");
                }
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
        }
    }
}