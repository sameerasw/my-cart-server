package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private UserService userService;

    @Transactional
    public Customer createCustomer(Customer customer) {
        // Create a new customer. Check if the email already exists in the database.
        if (userService.emailExists(customer.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        logger.info("Customer created: (" + customer.getId() + ") " + customer.getName());
        return customerRepository.save(customer);
    }

    public void purchaseTicket(Customer customer, long eventItemId) {
        // Purchase ticket for the customer for the given event. ReentrantLock is used to ensure that only one thread can access the ticket pool at a time.
        lock.lock();
        try {
            EventItem eventItem = eventRepository.findById(eventItemId).orElse(null);
            if (eventItem != null && eventItem.getTicketPool() != null) {
                ticketPoolService.removeTicket(eventItemId, customer);
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