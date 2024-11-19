package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.TicketPoolRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sameerasw.ticketin.server.Application.*;

@Service
public class TicketPoolService {
    private static final Logger logger = LoggerFactory.getLogger(TicketPoolService.class);

    @Autowired
    private TicketPoolRepository ticketPoolRepository;
    @Autowired
    private TicketService ticketService;

    private final Lock lock = new ReentrantLock();

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public  TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    public void removeTicket(Long eventItemId, Customer customer) {
        lock.lock();
        try {
//            TicketPool ticketPool = getTicketPoolByEventItemId(eventItemId);
            TicketPool ticketPool = ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
            if (ticketPool != null && ticketPool.getAvailableTickets() > 0) {
                Ticket ticket = ticketPool.getTickets().stream().filter(Ticket::isAvailable).findFirst().orElse(null);
                if (ticket != null) {
                    ticket.sellTicket();
                    ticket.setCustomer(customer);
                    ticketService.saveTicket(ticket);
                    logger.info(ANSI_GREEN + customer.getName() + " - Ticket " + ticket.getId() + " purchased for " + ticketPool.getEventName() + ANSI_RESET);
//                    return true;
                } else {
                    logger.info(ANSI_YELLOW + "No tickets available for the event: " + ticketPool.getEventName() + ANSI_RESET);
                }
            }
        } finally {
            lock.unlock();
        }
//        return false;
    }

    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
        }
    }
}