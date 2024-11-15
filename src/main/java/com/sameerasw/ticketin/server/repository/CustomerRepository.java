package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Customer findByName(String name, boolean isSimulated);
//    Customer findByEmail(String email, boolean isSimulated);

    List<Customer> findByisSimulated(boolean isSimulated);
}
