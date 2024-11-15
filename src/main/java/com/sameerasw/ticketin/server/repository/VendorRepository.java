package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
//    Vendor findByName(String name, boolean isSimulated);
//    Vendor findByEmail(String email, boolean isSimulated);

    List<Vendor> findByisSimulated(boolean isSimulated);
}
