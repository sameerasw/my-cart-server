package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import com.sameerasw.ticketin.server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketPool ticketPool;
    @Autowired
    private TicketService ticketService;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void purchaseTicket(Customer customer, long eventItemId) {
        EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
        if (eventItem != null && eventItem.getTicketPool() != null) {
            Ticket ticket = eventItem.getTicketPool().removeTicket(customer);
            if (ticket != null) {
                ticketService.saveTicket(ticket); // Save to persist changes
            }
        }
    }

    public List<Customer> getAllCustomers(boolean isSimulated) {
        return customerRepository.findByisSimulated(isSimulated);
    }
}
