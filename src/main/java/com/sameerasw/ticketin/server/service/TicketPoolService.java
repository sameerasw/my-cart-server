package com.sameerasw.ticketin.server.service;

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

    public TicketPool getTicketPoolByEventItemId(Long eventItemId) { // Changed method name
        return ticketPoolRepository.findByEventItemId(eventItemId); // Changed method call
    }

    // other methods as needed
}