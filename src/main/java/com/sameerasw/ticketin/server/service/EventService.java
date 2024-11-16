package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private EventRepository eventRepository;

    public EventItem getEventById(Long eventItemId) {
        return eventRepository.findById(eventItemId).orElse(null);
    }

//    public EventItem createEvent(EventItem eventItem, int maxPoolSize) {
//        TicketPool ticketPool = new TicketPool(maxPoolSize, eventItem);
//        ticketPoolService.createTicketPool(ticketPool);
//        eventItem.setTicketPool(ticketPool);
//        return eventRepository.save(eventItem);
//    }

    public EventItem createEvent(EventItem eventItem, int maxPoolSize) {
        // Save the EventItem FIRST
        EventItem savedEventItem = eventRepository.save(eventItem);

        // Now, create and save the TicketPool
        TicketPool ticketPool = new TicketPool(maxPoolSize, savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool); // Assign the saved TicketPool to the EventItem
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public EventItem createEvent(EventItem eventItem) {
        // Save the EventItem FIRST
        EventItem savedEventItem = eventRepository.save(eventItem);

        // Now, create and save the TicketPool
        TicketPool ticketPool = new TicketPool(eventItem.getTicketPool().getMaxPoolSize(), savedEventItem);
        ticketPoolService.createTicketPool(ticketPool);
        savedEventItem.setTicketPool(ticketPool); // Assign the saved TicketPool to the EventItem
        return eventRepository.save(savedEventItem); // Save the updated EventItem
    }

    public List<EventItem> getAllEvents(boolean b) {
        return eventRepository.findByisSimulated(b);
    }
}
