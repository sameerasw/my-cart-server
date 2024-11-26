package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.EventItemDTO;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private MappingService mappingService;

    @PostMapping
    public ResponseEntity<EventItemDTO> createEvent(@RequestBody EventItemDTO eventItemDTO) {
        EventItem eventItem = new EventItem(eventItemDTO.getEventName(), eventItemDTO.getEventLocation(), eventItemDTO.getEventDate(), eventItemDTO.getEventTime(), eventItemDTO.getTicketPrice(), eventItemDTO.getDetails(), eventItemDTO.getImage(), eventItemDTO.getVendorId(), eventItemDTO.getVendorName());
        return new ResponseEntity<>(mappingService.mapToEventItemDTO(eventService.createEvent(eventItem)), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EventItemDTO>> getAllEvents() {
        List<EventItemDTO> events = eventService.getAllEvents(true)
                .stream()
                .map(mappingService::mapToEventItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}