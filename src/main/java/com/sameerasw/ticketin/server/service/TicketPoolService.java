package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.CartItem;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sameerasw.ticketin.cli.Cli.logger;
import static com.sameerasw.ticketin.server.Application.*;

@Service
public class TicketPoolService {
    private final Lock lock = new ReentrantLock();
    @Autowired
    private TicketPoolRepository ticketPoolRepository;
    @Autowired
    private TicketService ticketService;

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    public boolean removeTicket(Long eventItemId, Customer customer) {
        // Remove ticket from the ticket pool. ReentrantLock is used to avoid multiple threads accessing the same ticket pool from customer purchases.
        lock.lock();
        boolean result = false;
        try {
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().stream().filter(Ticket::isAvailable).findFirst().orElse(null);
                if (ticket != null) {
                    ticket.sellTicket();
                    ticket.setCustomer(customer);
                    ticketService.saveTicket(ticket);
                    logger.info(ANSI_GREEN + customer.getName() + " - Ticket " + ticket.getId() + " purchased for " + ticketPool.getEventName() + " remaining tickets: " + ticketPool.getAvailableTickets() + ANSI_RESET);
                    result = true;
                } else {
                    logger.info(ANSI_YELLOW + "No tickets available for the event: " + ticketPool.getEventName() + ANSI_RESET);
                }
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    public boolean removeTicket(Long eventItemId, Customer customer, CartItem cartItem) {
        // Remove ticket from the ticket pool. ReentrantLock is used to avoid multiple threads accessing the same ticket pool from customer purchases.
        lock.lock();
        boolean result = false;
        try {
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().stream().filter(Ticket::isAvailable).findFirst().orElse(null);
                if (ticket != null) {
                    ticket.sellTicket();
                    ticket.setCustomer(customer);
                    ticket.setCartItem(cartItem);
                    ticketService.saveTicket(ticket);
                    logger.info(ANSI_GREEN + customer.getName() + " - Ticket " + ticket.getId() + " purchased for " + ticketPool.getEventName() + " remaining tickets: " + ticketPool.getAvailableTickets() + ANSI_RESET);
                    result = true;
                } else {
                    logger.info(ANSI_YELLOW + "No tickets available for the event: " + ticketPool.getEventName() + ANSI_RESET);
                }
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
        }
    }
}