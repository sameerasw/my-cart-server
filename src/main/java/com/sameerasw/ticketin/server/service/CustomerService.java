package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketPoolService ticketPoolService;
    @Autowired
    private TicketService ticketService;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public synchronized void purchaseTicket(Customer customer, long eventItemId) {
        EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
        if (eventItem != null && eventItem.getTicketPool() != null) {
            TicketPool ticketPool = eventItem.getTicketPool();
            Ticket ticket = ticketPoolService.removeTicket(ticketPool, customer);
            if (ticket != null) {
                ticketService.saveTicket(ticket);
                logger.info("Ticket " + ticket.getId() + " purchased successfully by customer: " + customer.getName() + " for event: " + eventItem.getName());
            } else {
                logger.info("No tickets available for the event, purchase denied. Try again later.");
            }
        }
    }

    public List<Customer> getAllCustomers(boolean isSimulated) {
        return customerRepository.findByisSimulated(isSimulated);
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}