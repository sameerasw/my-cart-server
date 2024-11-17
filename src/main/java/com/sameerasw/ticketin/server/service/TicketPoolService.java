package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPoolService {
    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    public TicketPool createTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) {
        return ticketPoolRepository.findByEventItemIdAndTicketsIsSoldFalse(eventItemId);
    }

    public synchronized Ticket removeTicket(TicketPool ticketPool, Customer customer) {
        if (ticketPool.getAvailableTickets() > 0) {
            Ticket ticket = ticketPool.getTickets().removeFirst();
            if (ticket != null) {
                ticket.setCustomer(customer);
                ticket.sellTicket();
//                ticketPool.setAvailableTickets(ticketPool.getAvailableTickets() - 1);
//                ticketPool.getTickets().remove(ticket);
//                ticketPoolRepository.save(ticketPool);
                return ticket;
            }
        }
        return null;
    }

    public void addTicket(TicketPool ticketPool, Ticket ticket) {
        if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize()) {
            ticketPool.getTickets().add(ticket);
//            ticketPool.setAvailableTickets(ticketPool.getAvailableTickets() + 1);
        }
    }
}