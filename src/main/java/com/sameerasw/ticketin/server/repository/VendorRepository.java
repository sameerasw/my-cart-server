package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByName(String name);
}
