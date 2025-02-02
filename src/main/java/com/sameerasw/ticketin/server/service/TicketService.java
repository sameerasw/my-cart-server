package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Item;
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

    public Item saveTicket(Item item) {
        try {
            Item savedItem = ticketRepository.save(item);
            return savedItem;
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("Ticket update failed due to concurrent modification", e);
        }
    }

    public List<Item> getTicketsByEventId(Long eventItemId) {
        return ticketRepository.findByEventItemId(eventItemId);
    }

    public List<Item> getAllTickets() {
        return ticketRepository.findAll();
    }
}