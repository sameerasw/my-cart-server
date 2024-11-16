package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    TicketPool findByEventItemId(Long eventItemId); // Changed to findByEventItemId
}