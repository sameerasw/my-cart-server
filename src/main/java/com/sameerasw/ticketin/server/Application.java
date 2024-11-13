package com.sameerasw.ticketin.server;

import com.sameerasw.ticketin.cli.Cli;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("TicketIn Application Started");

		Cli cli = new Cli();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("The following commands are available:\n" +
					"  list-tickets <vendorId>\n" +
					"  exit\n" +
					"Enter a command: ");
			String line = scanner.nextLine();
			String[] parts = line.split(" ");

			if (parts[0].equalsIgnoreCase("list-tickets")) {
				if (parts.length == 2) {
					try {
						long vendorId = Long.parseLong(parts[1]);
						cli.listTickets(vendorId);
					} catch (NumberFormatException e) {
						System.err.println("Invalid vendor ID.");
					}
				} else {
					System.err.println("Usage: list-tickets <vendorId>");
				}
			} else if (parts[0].equalsIgnoreCase("exit")) {
				System.out.println("Exiting...");
				break;
			} else {
				System.err.println("Invalid command.");
			}
		}
	}

}
