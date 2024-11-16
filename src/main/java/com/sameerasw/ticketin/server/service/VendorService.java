package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.repository.EventRepository;
import com.sameerasw.ticketin.server.repository.TicketRepository;
import com.sameerasw.ticketin.server.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketPool ticketPool;


    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void releaseTickets(Vendor vendor, Long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        if (eventItem != null && eventItem.getTicketPool() != null) {
            Thread thread = new Thread(() -> {
                while (true) {
                    try {
                        for (int i = 0; i < vendor.getTicketReleaseRate(); i++) {
                            Ticket ticket = new Ticket(eventItem, true);
                            eventItem.getTicketPool().addTicket(ticket);
                            ticketRepository.save(ticket);
                        }
                        Thread.sleep(vendor.getTicketReleaseRate() * 1000L);
                    } catch (InterruptedException e) {
                        //Handle interruption
                    }
                }
            });
            thread.start();
        }
    }

    public List<Vendor> getAllVendors(boolean isSimulated) {
        return vendorRepository.findByisSimulated(isSimulated);
    }
}