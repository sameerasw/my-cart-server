package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.dto.*;
import com.sameerasw.ticketin.server.model.*;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public TicketDTO mapToTicketDTO(Item item) {
        TicketDTO dto = new TicketDTO();
        dto.setId(item.getId());
        dto.setSold(item.isAvailable());
        dto.setEventId(item.getEventItem().getId());
        dto.setCustomerId(item.getCustomer() != null ? item.getCustomer().getId() : null);
        return dto;
    }

    public CustomerDTO mapToCustomerDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setTicketRetrievalRate(customer.getTicketRetrievalRate());
        return dto;
    }

    public VendorDTO mapToVendorDTO(Vendor vendor) {
        VendorDTO dto = new VendorDTO();
        dto.setId(vendor.getId());
        dto.setName(vendor.getName());
        dto.setEmail(vendor.getEmail());
        dto.setTicketReleaseRate(vendor.getTicketReleaseRate());
        return dto;
    }

    public EventItemDTO mapToEventItemDTO(EventItem eventItem) {
        EventItemDTO dto = new EventItemDTO();
        dto.setId(eventItem.getId());
        dto.setEventName(eventItem.getEventName());
        dto.setEventLocation(eventItem.getEventLocation());
        dto.setEventDate(eventItem.getEventDate());
        dto.setEventTime(eventItem.getEventTime());
        dto.setEventId(eventItem.getEventId());
        dto.setTicketPrice(eventItem.getTicketPrice());
        dto.setDetails(eventItem.getDetails());
        dto.setImage(eventItem.getImage());
        dto.setVendorId(eventItem.getVendor().getId());
        dto.setVendorName(eventItem.getVendor().getName());
        dto.setAvailableTickets(eventItem.getTicketPool().getAvailableTickets());
        return dto;
    }

    public TicketPoolDTO mapToTicketPoolDTO(TicketPool ticketPool) {
        TicketPoolDTO dto = new TicketPoolDTO();
        dto.setId(ticketPool.getPoolId());
        dto.setMaxPoolSize(ticketPool.getMaxPoolSize());
        dto.setAvailableTickets(ticketPool.getAvailableTickets());
        dto.setEventItemId(ticketPool.getEventItem().getId());
        return dto;
    }
}