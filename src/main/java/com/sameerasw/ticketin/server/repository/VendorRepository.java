package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Vendor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByisSimulated(boolean isSimulated);
}