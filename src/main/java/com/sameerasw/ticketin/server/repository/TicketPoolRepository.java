package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    TicketPool findByEventItemIdAndTicketsIsSoldFalse(Long eventItemId);

}