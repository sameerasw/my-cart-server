package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Rating;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import com.sameerasw.ticketin.server.repository.RatingRepository;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RatingRepository ratingRepository;

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

    public int getAvgRating(long eventItemId) {
//        int rating = 0;
//        int count = 0;
//        List<EventItem> eventItems = eventRepository.findByVendorId(eventItemId);
//        for (EventItem eventItem : eventItems) {
//            rating += ratingRepository.findRatingByEventItemId(eventItem.getId());
//            count++;
//        }
//        return rating / count;
        return ratingRepository.findRatingByEventItemId(eventItemId);
    }

    public void rateEvent(long eventItemId, int rating, long customerId) {
        EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (eventItem != null && customer != null) {
            Rating ratingObj = new Rating(rating, eventItem, customer);
            eventItem.addRating(ratingObj);
            eventItem.setAvgRating(getAvgRating(eventItemId));
            eventRepository.save(eventItem);
            logger.info("Rating added for EventItem: (" + eventItem.getId() + ") - " + eventItem.getName());
        }
    }

    public void updateEvent(EventItem eventItem) {
        eventRepository.save(eventItem);
    }
}
