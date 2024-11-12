package com.sameerasw.ticketin.be;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByName(String name);
}
