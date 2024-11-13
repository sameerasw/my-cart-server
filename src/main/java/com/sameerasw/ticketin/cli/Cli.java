package com.sameerasw.ticketin.cli;

import java.util.Scanner;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.CustomerService;
import com.sameerasw.ticketin.server.service.TicketService;
import com.sameerasw.ticketin.server.service.VendorService;

@Component
public class Cli {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate; // For REST calls

    public static void main(String[] args) {
        // ...
    }

    public void listTickets(long vendorId) {
        String url = "http://localhost:8081/vendors/" + vendorId + "/tickets";
        HttpEntity<Void> entity = new HttpEntity<>(null, new HttpHeaders()); // Empty entity
        ResponseEntity<List<Ticket>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Ticket>>() {});

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Ticket> tickets = response.getBody();
            System.out.println("Available Tickets for Vendor " + vendorId + ":");
            for (Ticket ticket : tickets) {
                System.out.println("  ID: " + ticket.getId() + ", Price: " + ticket.getPrice());
            }
        } else {
            System.err.println("Error fetching tickets: " + response.getStatusCode());
        }
    }

}