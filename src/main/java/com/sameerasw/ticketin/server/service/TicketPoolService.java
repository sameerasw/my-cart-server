package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.CartItem;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Item;
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
        return ticketPoolRepository.findByEventItemIdAndItemsIsSoldFalse(eventItemId);
    }

    public boolean removeTicket(Long eventItemId, Customer customer) {
        // Remove ticket from the ticket pool. ReentrantLock is used to avoid multiple threads accessing the same ticket pool from customer purchases.
        lock.lock();
        boolean result = false;
        try {
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndItemsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Item item = ticketPool.getTickets().stream().filter(Item::isAvailable).findFirst().orElse(null);
                if (item != null) {
                    item.sellTicket();
                    item.setCustomer(customer);
                    ticketService.saveTicket(item);
                    logger.info(ANSI_GREEN + customer.getName() + " - Ticket " + item.getId() + " purchased for " + ticketPool.getEventName() + " remaining tickets: " + ticketPool.getAvailableTickets() + ANSI_RESET);
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
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndItemsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Item item = ticketPool.getTickets().stream().filter(Item::isAvailable).findFirst().orElse(null);
                if (item != null) {
                    item.sellTicket();
                    item.setCustomer(customer);
                    item.setCartItem(cartItem);
                    ticketService.saveTicket(item);
                    logger.info(ANSI_GREEN + customer.getName() + " - Ticket " + item.getId() + " purchased for " + ticketPool.getEventName() + " remaining tickets: " + ticketPool.getAvailableTickets() + ANSI_RESET);
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

    public void addTicket(TicketPool ticketPool, Item item) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(item);
        }
    }
}