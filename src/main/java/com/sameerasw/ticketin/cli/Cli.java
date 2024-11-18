package com.sameerasw.ticketin.cli;

import java.util.List;
import java.util.Scanner;

import com.sameerasw.ticketin.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sameerasw.ticketin.server.service.CustomerService;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.TicketService;
import com.sameerasw.ticketin.server.service.VendorService;

@Component
public class Cli {
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

    public void start() {
        System.out.println("TicketIn CLI - Started");
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
                System.out.println("Exiting...");
                scanner.close();
                System.exit(0);
                break;
            case 12:
                startSimulation();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void startSimulation() {
        System.out.println("Starting simulation... Press Enter to stop.");
        List<Customer> customers = customerService.getAllCustomers(true);
        List<Vendor> vendors = vendorService.getAllVendors(true);
        List<EventItem> events = eventService.getAllEvents(true);

        for (Vendor vendor : vendors) {
            new Thread(() -> {
                final Long releaseRate = vendor.getTicketReleaseRate();
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(releaseRate * 1000);
                    } catch (InterruptedException e) {
                        logger.info("Thread interrupted.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                    vendorService.releaseTickets(vendor, events.get((int) (Math.random() * events.size())).getId());
                }
            }).start();
        }

        for (Customer customer : customers) {
            final Long retrievalRate = customer.getTicketRetrievalRate();
            Thread customerThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    customerService.purchaseTicket(customer, events.get((int) (Math.random() * events.size())).getId());
                    try {
                        Thread.sleep(retrievalRate * 1000);
                    } catch (InterruptedException e) {
                        logger.info("Thread interrupted.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            customerThread.start();
        }

        System.out.println("Running threads: " + Thread.activeCount());
        scanner.nextLine();
        System.out.println("Stopping simulation...");
        Thread.getAllStackTraces().keySet().stream()
                .filter(thread -> !thread.equals(Thread.currentThread()))
                .forEach(thread -> {
                    try {
                        thread.interrupt();
                    } catch (Exception e) {
                        logger.error("Error stopping thread: " + thread.getName(), e);
                    }
                });
    }

    private void displayMenu() {
        System.out.println("\n--- TicketIn CLI Menu ---\n" +
                "1. Create Vendor\n" +
                "2. List Vendors\n" +
                "3. Create Customer\n" +
                "4. List Customers\n" +
                "5. Create Event\n" +
                "6. List Events\n" +
                "7. List Tickets for Event\n" +
                "8. Buy Ticket\n" +
                "9. Release Tickets\n" +
                "10. View TicketPool\n" +
                "11. Exit\n" +
                "12. Start Simulation");
    }

    private int getIntegerInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private long getLongInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void createVendor() {
        String name = getStringInput("Enter vendor name: ");
        String email = getStringInput("Enter vendor email: ");
        int ticketReleaseRate = getIntegerInput("Enter ticket release rate: ");
        Vendor vendor = new Vendor(name, ticketReleaseRate);
        vendorService.createVendor(vendor);
        System.out.println("Vendor created successfully.");
    }

    private void listVendors() {
        List<Vendor> vendors = vendorService.getAllVendors(true);
        System.out.println("Vendors:");
        for (Vendor vendor : vendors) {
            System.out.println("  ID: " + vendor.getId() + ", Name: " + vendor.getName());
        }
    }

    private void createCustomer() {
        String name = getStringInput("Enter customer name: ");
        String email = getStringInput("Enter customer email: ");
        int ticketRetrievalRate = getIntegerInput("Enter ticket retrieval rate: ");
        Customer customer = new Customer(name, ticketRetrievalRate);
        customerService.createCustomer(customer);
        System.out.println("Customer created successfully.");
    }

    private void listCustomers() {
        List<Customer> customers = customerService.getAllCustomers(true);
        System.out.println("Customers:");
        for (Customer customer : customers) {
            System.out.println("  ID: " + customer.getId() + ", Name: " + customer.getName());
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

        EventItem eventItem = new EventItem(eventName, vendorService.getVendorById(vendorId), true);
        eventService.createEvent(eventItem, maxPoolSize);
        eventItem.createTicketPool(maxPoolSize);
        System.out.println("Event created successfully.");
    }

    private void listEvents() {
        List<EventItem> eventItems = eventService.getAllEvents(true);
        System.out.println("Events:");
        for (EventItem eventItem : eventItems) {
            System.out.println("  ID: " + eventItem.getId() + ", Name: " + eventItem.getEventName());
        }
    }

    private void listTicketsForEvent() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            List<Ticket> tickets = eventItem.getTicketPool().getTickets();
            System.out.println("Tickets for event " + eventItem.getEventName() + ":");
            for (Ticket ticket : tickets) {
                System.out.println("  ID: " + ticket.getId() + ", Available?: " + ticket.isAvailable());
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    private void buyTicket() {
        long customerId = getLongInput("Enter customer ID: ");
        long eventId = getLongInput("Enter event ID: ");
        Customer customer = customerService.getCustomerById(customerId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (customer != null && eventItem != null) {
            System.out.println("Ticket purchase requested.");
            customerService.purchaseTicket(customer, eventId);
        } else {
            System.out.println("Customer or Event not found.");
        }
    }

    private void releaseTickets() {
        long vendorId = getLongInput("Enter vendor ID: ");
        long eventId = getLongInput("Enter event ID: ");
        Vendor vendor = vendorService.getVendorById(vendorId);
        EventItem eventItem = eventService.getEventById(eventId);

        if (vendor != null && eventItem != null && eventItem.getVendor().getId().equals(vendorId)) {
            System.out.println("Tickets release requested.");
            vendorService.releaseTickets(vendor, eventId);
        } else {
            System.out.println("Vendor or Event not found, or they are not related.");
        }
    }

    private void viewTicketPool() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            System.out.println(eventItem.toString());
        } else {
            System.out.println("Event not found.");
        }
    }
}