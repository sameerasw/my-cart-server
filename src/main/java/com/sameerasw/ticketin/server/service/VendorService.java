package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.repository.VendorRepository;
import com.sameerasw.ticketin.server.model.Ticket;
import com.sameerasw.ticketin.server.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    // Add other service methods for Vendor operations

    public void releaseTickets(Vendor vendor) {
        Thread thread = new Thread(() -> {
            while (true) { // Simulate continuous ticket release
                try {
                    for (int i = 0; i < vendor.getTicketReleaseRate(); i++) {
                        Ticket newTicket = new Ticket();
                        newTicket.setVendor(vendor);
                        ticketPool.addTicket(newTicket);
                    }
                    Thread.sleep(vendor.getTicketReleaseRate() * 1000); // Adjust release rate
                } catch (InterruptedException e) {
                    // Handle interruption
                }
            }
        });
        thread.start();
    }

    public List<Vendor> getAllVendors(boolean isSimulated) {
        return vendorRepository.findByisSimulated(isSimulated);
    }

    public Vendor getVendorById(long vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }
}