package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByEventId(Long eventItemId) {
        return ticketRepository.findByEventItemId(eventItemId);
    }

}
