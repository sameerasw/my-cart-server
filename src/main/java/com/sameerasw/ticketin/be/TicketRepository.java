package com.sameerasw.ticketin.be;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByPrice(double price);
}
