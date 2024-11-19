package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByisSimulated(boolean isSimulated);
}
