package com.sameerasw.ticketin.server;

import com.sameerasw.ticketin.cli.Cli;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sameerasw.ticketin")
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println("TicketIn Application Started");

		// Initialize CLI
		Cli cli = ctx.getBean(Cli.class);
		cli.start(); // Call a method to start the CLI
	}
}