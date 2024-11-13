package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByPrice(double price);
}
