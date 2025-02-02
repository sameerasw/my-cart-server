package com.sameerasw.ticketin.server.controller;

import com.sameerasw.ticketin.server.dto.RatingDTO;
import com.sameerasw.ticketin.server.model.Rating;
import com.sameerasw.ticketin.server.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = "*")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO ratingDTO) {
        Rating rating = ratingService.addRating(ratingDTO.getRating(), ratingDTO.getEventItemId(), ratingDTO.getCustomerId());
        ratingDTO.setId(rating.getId());
        return new ResponseEntity<>(ratingDTO, HttpStatus.CREATED);
    }
}