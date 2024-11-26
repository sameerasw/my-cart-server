package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.TicketPoolDTO;
import com.sameerasw.ticketin.server.model.TicketPool;
import com.sameerasw.ticketin.server.service.MappingService;
import com.sameerasw.ticketin.server.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticketpools")
public class TicketPoolController {
    @Autowired
    private TicketPoolService ticketPoolService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("/{eventItemId}")
    public ResponseEntity<TicketPoolDTO> getTicketPoolByEventItemId(@PathVariable Long eventItemId) {
        TicketPool ticketPool = ticketPoolService.getTicketPoolByEventItemId(eventItemId);
        return ticketPool != null ? new ResponseEntity<>(mappingService.mapToTicketPoolDTO(ticketPool), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}