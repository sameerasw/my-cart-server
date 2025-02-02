package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Rating;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.EventRepository;
import com.sameerasw.ticketin.server.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Rating addRating(int ratingValue, Long eventItemId, Long customerId) {
        EventItem eventItem = eventRepository.findById(eventItemId).orElseThrow(() -> new IllegalArgumentException("Invalid event item ID"));
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        Rating rating = new Rating(ratingValue, eventItem, customer);
        ratingRepository.save(rating);

        updateAvgRating(eventItemId);

        return rating;
    }

    private void updateAvgRating(Long eventItemId) {
        double avgRating = ratingRepository.findAvgRatingByEventItemId(eventItemId);
        EventItem eventItem = eventRepository.findById(eventItemId).orElseThrow(() -> new IllegalArgumentException("Invalid event item ID"));
        eventItem.setAvgRating((int) avgRating);
        eventRepository.save(eventItem);
    }
}