package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.EventItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<EventItem, Long> {
    List<EventItem> findByVendorId(Long vendorId);

    List<EventItem> findByisSimulated(boolean b);
}
