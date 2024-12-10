package com.sameerasw.ticketin.cli;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Vendor;
import com.sameerasw.ticketin.server.service.CustomerService;
import com.sameerasw.ticketin.server.service.EventService;
import com.sameerasw.ticketin.server.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static com.sameerasw.ticketin.server.Application.*;

@Component
public class Cli {
    public static final Logger logger = LoggerFactory.getLogger(Cli.class);

    @Autowired
    private VendorService vendorService;
    @Autowired
    private EventService eventService;
    @Autowired
    private CustomerService customerService;

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        logger.info(ANSI_GREEN + "TicketIn CLI - Started" + ANSI_RESET);
        while (true) {
            displayMenu();
            int choice = getIntegerInput(ANSI_CYAN + "Enter your choice: " + ANSI_RESET);
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
                howManyThreads();
                break;
            case 8:
                viewTicketPool();
                break;
            case 9:
                configureSimulation();
                break;
            case 10:
                startSimulation();
                break;
            case 11:
                System.out.println("Exiting...");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println(ANSI_RED + "Invalid choice." + ANSI_RESET);
        }
    }

    private void displayMenu() {
        System.out.println(
                ANSI_RED + "<< Server is running. Use the CLI to interact with the server for testing/ simulation purposes. >>\n" + ANSI_RESET +
                "\n---- " + ANSI_GREEN + "TicketIn CLI Menu" + ANSI_RESET + " ----\n" +
                "---------------------------\n" +
                "1. Create Simulated Vendor\n" +
                "2. List Simulated Vendors\n" +
                "3. Create Simulated Customer\n" +
                "4. List Simulated Customers\n" +
                "5. Create Simulated Event\n" +
                "6. List Simulated Events\n" +
                "7. How Many Threads are Running?\n" +
                "8. View Simulated Ticket Pool\n" +
                "---------------------------\n" +
                "9. Configure Simulation\n" +
                "10. Start Simulation. [Enter] to stop\n" +
                "---------------------------\n" +
                "11. Exit" +
                "\n---------------------------\n"
        );
    }

    private int getIntegerInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayMenu();
                System.out.println(ANSI_RED + "Invalid input. Please enter a number." + ANSI_RESET);
            }
        }
    }

    private void configureSimulation() {
        System.out.println("Configure the simulation");
        int numVendors = getIntegerInput("Enter the number of vendors: ");
        int numCustomers = getIntegerInput("Enter the number of customers: ");
        System.out.println(ANSI_YELLOW + "Creating simulation data... Please wait. This may take a while." + ANSI_RESET);

        for (int i = 0; i < numVendors; i++) {
            int ticketReleaseRate = (int) (Math.random() * 5) + 1;
            Vendor vendor = new Vendor("Simulated_Vendor" + i, getRandomeEmail("Vendor " + i), ticketReleaseRate);
            vendorService.createVendor(vendor);
        }
        logger.info("Vendors created");

        for (int i = 0; i < numCustomers; i++) {
            int ticketRetrievalRate = (int) (Math.random() * 5) + 1;
            Customer customer = new Customer("Simulated_Customer" + i, getRandomeEmail("Customer " + i), ticketRetrievalRate);
            customerService.createCustomer(customer);
        }
        logger.info("Customers created");

        for (int i = 0; i < numVendors; i++) {
            int numTicketsPerEvent = (int) (Math.random() * 10) + 1;
            Vendor vendor = vendorService.getAllVendors(true).get((int) (Math.random() * numVendors));
            EventItem eventItem = new EventItem("Simulated_Event " + i, vendor, true);
            eventService.createEvent(eventItem, numTicketsPerEvent);
            eventItem.createTicketPool(numTicketsPerEvent);
        }

        System.out.println(ANSI_GREEN + "Created " + numVendors + " vendors, " + numCustomers + " customers, and " + numVendors + " events.\n Simulation is ready." + ANSI_RESET);
    }

    private void startSimulation() {
        logger.info(ANSI_GREEN + "Simulation started. Press Enter to stop the simulation." + ANSI_RESET);
        List<Customer> customers = customerService.getAllCustomers(true);
        List<Vendor> vendors = vendorService.getAllVendors(true);
        List<EventItem> events = eventService.getAllEvents(true);
        final boolean[] isSimulating = {true};

        if (customers.isEmpty() || vendors.isEmpty() || events.isEmpty()) {
            logger.warn(ANSI_RED + "Simulation cannot start. Ensure there are vendors, customers, and events." + ANSI_RESET);
            return;
        }

        for (Vendor vendor : vendors) {
            new Thread(new VendorSimulation(vendor, events, vendorService, isSimulating)).start();
        }

        for (Customer customer : customers) {
            new Thread(new CustomerSimulation(customer, events, customerService, isSimulating)).start();
        }

        howManyThreads();
        scanner.nextLine();
        logger.info(ANSI_RED + "Simulation stopped." + ANSI_RESET);
        isSimulating[0] = false;

        Thread.currentThread().interrupt();
    }

    private void howManyThreads() {
        logger.info(ANSI_GREEN + "Running threads: " + Thread.activeCount() + ANSI_RESET);
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
                System.out.println(ANSI_RED + "Invalid input. Please enter a number." + ANSI_RESET);
            }
        }
    }

    private String getRandomeEmail(String name) {
        // Generate a random email address
        return name.toLowerCase().replace(" ", "") + (int) (Math.random() * 1000) + "@example.com";
    }

    private void createVendor() {
        String name = getStringInput("Enter vendor name: ");
        int ticketReleaseRate = getIntegerInput("Enter ticket release rate: ");
        Vendor vendor = new Vendor(name, getRandomeEmail(name), ticketReleaseRate);
        vendorService.createVendor(vendor);
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
        int ticketRetrievalRate = getIntegerInput("Enter ticket retrieval rate: ");
        Customer customer = new Customer(name, getRandomeEmail(name), ticketRetrievalRate);
        customerService.createCustomer(customer);
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
        int maxPoolSize = getIntegerInput("Enter max pool size: ");

        EventItem eventItem = new EventItem(eventName, vendorService.getVendorById(vendorId), true);
        eventService.createEvent(eventItem, maxPoolSize);
        eventItem.createTicketPool(maxPoolSize);
    }

    private void listEvents() {
        List<EventItem> eventItems = eventService.getAllEvents(true);
        System.out.println("Events:");
        for (EventItem eventItem : eventItems) {
            System.out.println("  ID: " + eventItem.getId() + ", Name: " + eventItem.getEventName());
        }
    }

    private void viewTicketPool() {
        long eventId = getLongInput("Enter event ID: ");
        EventItem eventItem = eventService.getEventById(eventId);
        if (eventItem != null) {
            System.out.println(eventItem.toString());
        } else {
            System.out.println(ANSI_RED + "Event not found." + ANSI_RESET);
        }
    }
}