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
    @Autowired
    private EventService eventService;


    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void releaseTickets(Vendor vendor, Long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        if (eventItem != null) {
            Thread thread = new Thread(() -> {
                            Ticket ticket = new Ticket(eventItem, true);
                            System.out.println("Ticket created: " + ticket);
                            ticketRepository.save(ticket);
                            System.out.println("Ticket saved: " + ticket);
//                            eventItem.getTicketPool().addTicket(ticket);
//                            System.out.println("Ticket added to pool: " + ticket);
//                            System.out.println("Ticket release completed: " + ticket);
//                        Thread.sleep(vendor.getTicketReleaseRate() * 1000L);
            });
            thread.start();
        } else {
            System.out.println("Event not found or ticket pool not created");
        }
    }

    public List<Vendor> getAllVendors(boolean isSimulated) {
        return vendorRepository.findByisSimulated(isSimulated);
    }

    public Vendor getVendorById(long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }
}