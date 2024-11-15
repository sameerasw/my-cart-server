package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionService transactionService;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Add other service methods for Customer operations

    public void purchaseTicket(Customer customer, long vendorId) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Ticket ticket = ticketPool.removeTicket(vendorId);
                    if (ticket != null) {
                        // Create a Transaction
                        Transaction transaction = new Transaction();
                        transaction.setCustomer(customer);
                        transaction.setTicket(ticket);
                        transactionService.saveTransaction(transaction);
                    }
                    Thread.sleep(customer.getTicketRetrievalRate() * 1000); // Adjust retrieval rate
                } catch (InterruptedException e) {
                    // Handle interruption
                }
            }
        });
        thread.start();
    }

    public List<Customer> getAllCustomers(boolean isSimulated) {
        return customerRepository.findByisSimulated(isSimulated);
    }
}