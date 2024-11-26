package com.sameerasw.ticketin.server.controller;


import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventItem> getEventById(@PathVariable Long eventId) {
        EventItem eventItem = eventService.getEventById(eventId);
        return eventItem != null ? new ResponseEntity<>(eventItem, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EventItem> createEvent(@RequestBody EventItem eventItem) {
        return new ResponseEntity<>(eventService.createEvent(eventItem), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventItem>> getAllEvents() {
        List<EventItem> events = eventService.getAllEvents(true);
        return events != null ? new ResponseEntity<>(events, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}