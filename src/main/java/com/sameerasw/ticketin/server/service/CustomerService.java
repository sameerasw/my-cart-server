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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.sameerasw.ticketin.server.Application.*;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final Lock lock = new ReentrantLock();

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

    public  void purchaseTicket(Customer customer, long eventItemId) {
        lock.lock();
        try {
            EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
            if (eventItem != null && eventItem.getTicketPool() != null) {
//                TicketPool ticketPool = eventItem.getTicketPool();
                ticketPoolService.removeTicket(eventItemId, customer);
//
//                if (!response) {
//                    logger.info(ANSI_YELLOW + customer.getName() + " - No tickets available for: " + eventItem.getName() + ANSI_RESET);
//                }
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Customer> getAllCustomers(boolean isSimulated) {
        return customerRepository.findByisSimulated(isSimulated);
    }

    public Customer getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}