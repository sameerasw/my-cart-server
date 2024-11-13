package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
}
