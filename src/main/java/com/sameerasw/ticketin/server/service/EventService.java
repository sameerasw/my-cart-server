package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public EventItem getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public EventItem createEvent(EventItem eventItem) {
        return eventRepository.save(eventItem);
    }

    public List<EventItem> getAllEvents(boolean b) {
        return eventRepository.findByisSimulated(b);
    }
}
