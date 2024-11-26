package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        try {
            return ticketRepository.save(ticket);
        } catch (OptimisticLockingFailureException e) {
            // handle the exception, e.g., retry or notify the user
            throw new RuntimeException("Ticket update failed due to concurrent modification", e);
        }
    }

    public List<Ticket> getTicketsByEventId(Long eventItemId) {
        return ticketRepository.findByEventItemId(eventItemId);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
