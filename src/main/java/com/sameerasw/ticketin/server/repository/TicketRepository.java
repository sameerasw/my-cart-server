package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByPrice(double price);

    List<Ticket> findByVendorId(long vendorId);

    //find by vendor id and isSimulated
    List<Ticket> findByVendorIdAndIsSimulated(long vendorId, boolean isSimulated);
}
