package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Item;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.repository.EventRepository;
import com.sameerasw.ticketin.server.repository.TicketPoolRepository;
import com.sameerasw.ticketin.server.repository.TicketRepository;
import com.sameerasw.ticketin.server.repository.VendorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sameerasw.ticketin.server.Application.*;

@Service
public class VendorService {
    private static final Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private TicketPoolRepository ticketPoolRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public Vendor createVendor(Vendor vendor) {
        // Create a new vendor. Checks if the email already exists.
        if (userService.emailExists(vendor.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        logger.info("Vendor created: (" + vendor.getId() + ") " + vendor.getName());
        return vendorRepository.save(vendor);
    }

    @Transactional
    public void releaseTickets(Vendor vendor, Long eventId) {
        // Release a ticket for an event. Checks if the event exists and if the ticket pool is full.
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        boolean isSimulated = eventItem.isSimulated();
        if (eventItem != null) {
            TicketPool ticketPool = eventItem.getTicketPool();
            if (ticketPool != null) {
                if (ticketPool.getAvailableTickets() < ticketPool.getMaxPoolSize() || !isSimulated) {
                    Item item = new Item(eventItem, isSimulated);
                    ticketRepository.save(item);
                    ticketPoolService.addTicket(ticketPool, item);
                    ticketPoolRepository.save(ticketPool);
                    logger.info(ANSI_CYAN + vendor.getName() + " - Released ticket: " + item.getId() + " for: " + eventItem.getName() + " remaining tickets: " + ticketPool.getAvailableTickets() + ANSI_RESET);
                } else {
                    logger.info(ANSI_YELLOW + vendor.getName() + " - Ticket pool is full for: " + eventItem.getName() + ANSI_RESET);
                }
            } else {
                logger.info("Ticket pool not found");
            }
        } else {
            logger.info("Event not found");
        }
    }

    public List<Vendor> getAllVendors(boolean isSimulated) {
        return vendorRepository.findByisSimulated(isSimulated);
    }

    public Vendor getVendorById(long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    public Vendor getVendorByEventId(long eventId) {
        EventItem eventItem = eventRepository.findById(eventId).orElse(null);
        if (eventItem != null) {
            return eventItem.getVendor();
        } else {
            return null;
        }
    }
}