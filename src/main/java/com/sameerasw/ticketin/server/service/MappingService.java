package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.dto.*;
import com.sameerasw.ticketin.server.model.*;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EventRepository eventRepository;

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

    public ItemDTO mapToEventItemDTO(EventItem eventItem) {
        ItemDTO dto = new ItemDTO();
        dto.setId(eventItem.getId());
        dto.setProductName(eventItem.getEventName());
        dto.setProductLocation(eventItem.getEventLocation());
        dto.setProductDate(eventItem.getEventDate());
        dto.setProductTime(eventItem.getEventTime());
        dto.setEventId(eventItem.getEventId());
        dto.setProductPrice(eventItem.getTicketPrice());
        dto.setDetails(eventItem.getDetails());
        dto.setImage(eventItem.getImage());
        dto.setVendorId(eventItem.getVendor().getId());
        dto.setVendorName(eventItem.getVendor().getName());
        dto.setAvailableTickets(eventItem.getTicketPool().getAvailableTickets());
        dto.setAvgRating(eventItem.getAvgRating());
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

    public CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setCustomerId(cartItem.getCustomer().getId());
        dto.setProductId(cartItem.getEventItem().getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProductName(cartItem.getEventItem().getEventName());
        dto.setProductPrice(cartItem.getEventItem().getTicketPrice());
        dto.setImage(cartItem.getEventItem().getImage());
        return dto;
    }

    public CartItem mapToCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDTO.getId());
        cartItem.setCustomer(customerRepository.findById(cartItemDTO.getCustomerId()).orElse(null));
        cartItem.setEventItem(eventRepository.findById(cartItemDTO.getProductId()).orElse(null));
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }

    public RatingDTO mapToRatingDTO(Rating rating) {
        RatingDTO dto = new RatingDTO();
        dto.setId(rating.getId());
        dto.setRating(rating.getRating());
        dto.setProductId(rating.getEventItem().getId());
        dto.setCustomerId(rating.getCustomer().getId());
        return dto;
    }

}