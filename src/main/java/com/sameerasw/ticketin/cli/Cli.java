package com.sameerasw.ticketin.cli;

import java.util.List;
import java.util.Scanner;

import com.sameerasw.ticketin.server.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public static void main(String[] args) {
        // ..
    }

    public void listTickets(long vendorId) {
        List<Ticket> tickets = ticketService.getTicketsByVendorId(vendorId);
        System.out.println("Available Tickets for Vendor " + vendorId + ":");
        for (Ticket ticket : tickets) {
            System.out.println("  ID: " + ticket.getId() + ", Price: " + ticket.getPrice());
        }
    }

    public void addTicket(long vendorId, double price) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        Ticket ticket = new Ticket();
        ticket.setVendor(vendor);
        ticket.setPrice(price);
        ticketService.saveTicket(ticket);
        System.out.println("Ticket added successfully.");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("The following commands are available:\n" +
                    "  1. List tickets for a vendor {list-tickets <vendorId>}\n" +
                    "  2. Create a vendor\n" +
                    "  3. List all vendors\n" +
                    "  4. Create a customer\n" +
                    "  5. List all customers\n" +
                    "  6. Add a ticket to a vendor {add-ticket <vendorId> <price>}\n" +
                    "  7. Exit\n" +
                    "Enter a command (number or text): ");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            String command = parts[0];
            if (command.equalsIgnoreCase("1") || command.equalsIgnoreCase("list-tickets")) {
                if (parts.length == 2) {
                    try {
                        long vendorId = Long.parseLong(parts[1]);
                        listTickets(vendorId);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid vendor ID.");
                    }
                } else {
                    System.err.println("Usage: list-tickets <vendorId>");
                }
            } else if (command.equalsIgnoreCase("7") || command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            } else if (command.equalsIgnoreCase("2") || (command.equalsIgnoreCase("create") && parts.length == 2 && parts[1].equalsIgnoreCase("vendor"))) {
                System.out.print("Enter vendor name: ");
                String name = scanner.nextLine();
                System.out.print("Enter max ticket pool size: ");
                int maxTicketPoolSize = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter ticket release rate: ");
                int ticketReleaseRate = Integer.parseInt(scanner.nextLine());

                Vendor vendor = new Vendor(name, maxTicketPoolSize, ticketReleaseRate);
                vendorService.saveVendor(vendor);
                System.out.println("Vendor created successfully.");
            } else if (command.equalsIgnoreCase("3") || (command.equalsIgnoreCase("list") && parts.length == 2 && parts[1].equalsIgnoreCase("vendors"))) {
                List<Vendor> vendors = vendorService.getAllVendors();
                System.out.println("Vendors:");
                for (Vendor vendor : vendors) {
                    System.out.println("  ID: " + vendor.getId() + ", Name: " + vendor.getName());
                }
            } else if (command.equalsIgnoreCase("6") || command.equalsIgnoreCase("add-ticket")) {
                if (parts.length == 3) {
                    try {
                        long vendorId = Long.parseLong(parts[1]);
                        double price = Double.parseDouble(parts[2]);
                        addTicket(vendorId, price);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid vendor ID or price.");
                    }
                } else {
                    System.err.println("Usage: add-ticket <vendorId> <price>");
                }
            } else if (command.equalsIgnoreCase("4") || (command.equalsIgnoreCase("create") && parts.length == 2 && parts[1].equalsIgnoreCase("customer"))) {
                System.out.print("Enter customer name: ");
                String name = scanner.nextLine();
                System.out.print("Enter customer email: ");
                String email = scanner.nextLine();
                System.out.print("Enter ticket retrieval rate: ");
                int ticketRetrievalRate = Integer.parseInt(scanner.nextLine());

                Customer customer = new Customer(name, email, ticketRetrievalRate);
                customerService.saveCustomer(customer);
                System.out.println("Customer created successfully.");
            } else if (command.equalsIgnoreCase("5") || (command.equalsIgnoreCase("list") && parts.length == 2 && parts[1].equalsIgnoreCase("customers"))) {
                List<Customer> customers = customerService.getAllCustomers();
                System.out.println("Customers:");
                for (Customer customer : customers) {
                    System.out.println("  ID: " + customer.getId() + ", Name: " + customer.getName());
                }
            } else {
                System.err.println("Invalid command.");
            }
        }
    }
}