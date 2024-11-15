package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.repository.TicketRepository;
import com.sameerasw.ticketin.server.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByVendorId(long vendorId, boolean isSimulated) {
        // get tikets by vendor id where the vendor isSimulated true
        return ticketRepository.findByVendorIdAndIsSimulated(vendorId, isSimulated);

        // Add other service methods for Ticket operations
    }
}