package com.sameerasw.ticketin.server;

import com.sameerasw.ticketin.cli.Cli;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.sameerasw.ticketin")
public class Application {

	// declare the ansi color codes
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println("TicketIn Application Started");

		// Initialize CLI
		Cli cli = ctx.getBean(Cli.class);
		cli.start(); // Call a method to start the CLI
	}
}