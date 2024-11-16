package com.sameerasw.ticketin.cli;

import java.util.List;
import java.util.Scanner;

import com.sameerasw.ticketin.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sameerasw.ticketin.server.service.CustomerService;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.TicketService;
import com.sameerasw.ticketin.server.service.VendorService;

@Component
public class Cli implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Cli.class);

    @Autowired
    private VendorService vendorService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestTemplate restTemplate;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void start() {
        logger.info("TicketIn CLI - Started");
        while (true) {
            displayMenu();
            int choice = getIntegerInput("Enter your choice: ");
            processChoice(choice);
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                createVendor();
                break;
            case 2:
                listVendors();
                break;
            case 3:
                createCustomer();
                break;
            case 4:
                listCustomers();
                break;
            case 5:
                createEvent();
                break;
            case 6:
                listEvents();
                break;
            case 7:
                listTicketsForEvent();
                break;
            case 8:
                buyTicket();
                break;
            case 9:
                releaseTickets();
                break;
            case 10:
                viewTicketPool();
                break;
            case 11:
                logger.info("Exiting...");
                scanner.close();
                System.exit(0);
                break;
            default:
                logger.info("Invalid choice.");
        }
    }

    private void displayMenu() {
        logger.info("\n--- TicketIn CLI Menu ---");
        logger.info("1. Create Vendor");
        logger.info("2. List Vendors");
        logger.info("3. Create Customer");
        logger.info("4. List Customers");
        logger.info("5. Create Event");
        logger.info("6. List Events");
        logger.info("7. List Tickets for Event");
        logger.info("8. Buy Ticket");
        logger.info("9. Release Tickets");
        logger.info("10. View TicketPool");
        logger.info("11. Exit");
    }

    private int getIntegerInput(String prompt) {
        while (true) {
            logger.info(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                logger.info("Invalid input. Please enter a number.");
            }
        }
    }

    private String getStringInput(String prompt) {
        logger.info(prompt);
        return scanner.nextLine();
    }

    private long getLongInput(String prompt) {
        while (true) {
            logger.info(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                logger.info("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            logger.info(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                logger.info("Invalid input. Please enter a number.");
            }
        }
    }

    private void createVendor() {
        String name = getStringInput("Enter vendor name: ");
        String email = getStringInput("Enter vendor email: ");
        int ticketReleaseRate = getIntegerInput("Enter ticket release rate: ");
        Vendor vendor = new Vendor(name, email, ticketReleaseRate);
        vendorService.createVendor(vendor);
        logger.info("Vendor created successfully.");
    }

    private void listVendors() {
        List<Vendor> vendors = vendorService.getAllVendors(true);
        logger.info("Vendors:");
        for (Vendor vendor : vendors) {
            logger.info("  ID: " + vendor.getId() + ", Name: " + vendor.getName());
        }
    }

    private void createCustomer() {
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter customer email: ");
        int ticketRetrievalRate = getIntegerInput("Enter ticket retrieval rate: ");
        Customer customer = new Customer(name, email, ticketRetrievalRate);
        customerService.createCustomer(customer);
        logger.info("Customer created successfully.");
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getAllCustomers(true);
        logger.info("Customers:");
        for (Customer customer : customers) {
            logger.info("  ID: " + customer.getId() + ", Name: " + customer.getName());
        }
    }

    private void createEvent() {
        long vendorId = getLongInput("Enter vendor ID: ");
        String eventName = getStringInput("Enter event name: ");
        String eventLocation = getStringInput("Enter event location: ");
        String eventDate = getStringInput("Enter event date (yyyy-MM-dd): ");
        String eventTime = getStringInput("Enter event time (HH:mm): ");
        double ticketPrice = getDoubleInput("Enter ticket price: ");
        int maxPoolSize = getIntegerInput("Enter max pool size: ");

        EventItem eventItem = new EventItem(eventName, eventLocation, eventDate, eventTime, ticketPrice, vendorService.getVendorById(vendorId), true);
        eventService.createEvent(eventItem, maxPoolSize);
        eventItem.createTicketPool(maxPoolSize);
        logger.info("Event created successfully.");
    }

    private void listEvents() {
        List<EventItem> eventItems = eventService.getAllEvents(true);
        logger.info("Events:");
        for (EventItem eventItem : eventItems) {
            logger.info("  ID: " + eventItem.getId() + ", Name: " + eventItem.getEventName());
        }
    }

    private void listTicketsForEvent() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            List<Ticket> tickets = eventItem.getTicketPool().getTickets();
            logger.info("Tickets for event " + eventItem.getEventName() + ":");
            for (Ticket ticket : tickets) {
                logger.info("  ID: " + ticket.getId() + ", Available?: " + ticket.isAvailable());
            }
        } else {
            logger.info("Event not found.");
        }
    }

    private void buyTicket() {
        long customerId = getLongInput("Enter customer ID: ");
        long eventId = getLongInput("Enter event ID: ");
        Customer customer = customerService.getCustomerById(customerId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (customer != null && eventItem != null) {
            logger.info("Ticket purchase requested.");
            customerService.purchaseTicket(customer, eventId);
        } else {
            logger.info("Customer or Event not found.");
        }
    }

    private void releaseTickets() {
        long vendorId = getLongInput("Enter vendor ID: ");
        long eventId = getLongInput("Enter event ID: ");
        Vendor vendor = vendorService.getVendorById(vendorId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (vendor != null && eventItem != null && eventItem.getVendor().getId().equals(vendorId)) {
            logger.info("Tickets release requested.");
            vendorService.releaseTickets(vendor, eventId);
        } else {
            logger.info("Vendor or Event not found, or they are not related.");
        }
    }

    private void viewTicketPool() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            logger.info(eventItem.toString());
        } else {
            logger.info("Event not found.");
        }
    }
}