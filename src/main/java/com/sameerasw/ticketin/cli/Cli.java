package com.sameerasw.ticketin.cli;

import java.util.List;
import java.util.Scanner;

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

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("The following commands are available:\n" +
                    "  list-tickets <vendorId>\n" +
                    "  create vendor\n" +
                    "  list vendors\n" +
                    "  exit\n" +
                    "Enter a command: ");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            if (parts[0].equalsIgnoreCase("list-tickets")) {
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
            } else if (parts[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            } else if (parts[0].equalsIgnoreCase("create") && parts.length == 2 && parts[1].equalsIgnoreCase("vendor")) {
                System.out.print("Enter vendor name: ");
                String name = scanner.nextLine();
                System.out.print("Enter max ticket pool size: ");
                int maxTicketPoolSize = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter ticket release rate: ");
                int ticketReleaseRate = Integer.parseInt(scanner.nextLine());

                Vendor vendor = new Vendor(name, maxTicketPoolSize, ticketReleaseRate);
                vendorService.saveVendor(vendor);
                System.out.println("Vendor created successfully.");
            } else if (parts[0].equalsIgnoreCase("list") && parts.length == 2 && parts[1].equalsIgnoreCase("vendors")) {
                List<Vendor> vendors = vendorService.getAllVendors();
                System.out.println("Vendors:");
                for (Vendor vendor : vendors) {
                    System.out.println("  ID: " + vendor.getId() + ", Name: " + vendor.getName());
                }
            } else {
                System.err.println("Invalid command.");
            }
        }
    }
}