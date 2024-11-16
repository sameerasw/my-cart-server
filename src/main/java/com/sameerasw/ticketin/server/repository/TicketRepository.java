package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventItemId(Long eventItemId);
}
