package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Item, Long> {
    List<Item> findByEventItemId(Long eventItemId);
}
