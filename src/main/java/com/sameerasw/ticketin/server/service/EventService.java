package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sameerasw.ticketin.cli.Cli.logger;

@Service
public class EventService {
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private EventRepository eventRepository;

    public EventItem getEventById(Long eventItemId) {
        return eventRepository.findById(eventItemId).orElse(null);
    }


    public EventItem createEvent(EventItem eventItem, int maxPoolSize) {
        // Save the EventItem first. This is important because the EventItem needs to have an ID before creating the TicketPool
        EventItem savedEventItem = eventRepository.save(eventItem);

        // Creates and saves the TicketPool
        TicketPool ticketPool = new TicketPool(maxPoolSize, savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool);
        logger.info("TicketPool created for EventItem: (" + savedEventItem.getId() + ") - " + savedEventItem.getName());
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public EventItem createEvent(EventItem eventItem) {
        // Save the EventItem FIRST
        EventItem savedEventItem = eventRepository.save(eventItem);

        // Creates and saves the TicketPool
        TicketPool ticketPool = new TicketPool(savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool);
        logger.info("TicketPool created for EventItem: (" + savedEventItem.getId() + ") - " + savedEventItem.getName());
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public List<EventItem> getAllEvents(boolean isSimulated) {
        return eventRepository.findByisSimulated(isSimulated);
    }

    public List<EventItem> getVendorEvents(long vendorId) {
        return eventRepository.findByVendorId(vendorId);
    }
}
