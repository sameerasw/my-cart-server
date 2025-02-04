package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.ItemDTO;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.MappingService;
import com.sameerasw.ticketin.server.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sameerasw.ticketin.cli.Cli.logger;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "*")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private VendorService vendorService;

    // Create a new event
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody ItemDTO itemDTO) {
        try {
            if (itemDTO.getProductName() == null || itemDTO.getProductLocation() == null || itemDTO.getProductDate() == null || itemDTO.getProductTime() == null || itemDTO.getProductPrice() == 0 || itemDTO.getDetails() == null || itemDTO.getImage() == null || itemDTO.getVendorId() == null || itemDTO.getVendorName() == null) {
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            } else {
                logger.info("Creating event: " + itemDTO.getProductName() + " by vendor: " + itemDTO.getVendorName() + " with ID: " + itemDTO.getVendorId());
                Vendor vendor = vendorService.getVendorById(itemDTO.getVendorId());
                EventItem eventItem = new EventItem(itemDTO.getProductName(), itemDTO.getProductLocation(), itemDTO.getProductDate(), itemDTO.getProductTime(), itemDTO.getProductPrice(), itemDTO.getDetails(), itemDTO.getImage(), vendor);
                EventItem createdEvent = eventService.createEvent(eventItem);
                return new ResponseEntity<>(mappingService.mapToEventItemDTO(createdEvent), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all events
    @GetMapping("/list")
    public ResponseEntity<List<ItemDTO>> getAllEvents() {
        List<ItemDTO> events = eventService.getAllEvents(false)
                .stream()
                .map(mappingService::mapToEventItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Get all events by vendor
    @GetMapping("/{vendorId}/list")
    public ResponseEntity<List<ItemDTO>> getVendorEvents(@PathVariable long vendorId) {
        List<ItemDTO> events = eventService.getVendorEvents(vendorId)
                .stream()
                .map(mappingService::mapToEventItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Get specific event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<ItemDTO> getEventById(@PathVariable long eventId) {
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mappingService.mapToEventItemDTO(eventItem), HttpStatus.OK);
    }
}