// src/main/java/com/sameerasw/ticketin/server/service/RatingService.java
package com.sameerasw.ticketin.server.service;

import com.sameerasw.ticketin.server.model.Customer;
import com.sameerasw.ticketin.server.model.EventItem;
import com.sameerasw.ticketin.server.model.Rating;
import com.sameerasw.ticketin.server.repository.CustomerRepository;
import com.sameerasw.ticketin.server.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private CustomerRepository customerRepository;

    public Rating addRating(int ratingValue, long eventItemId, long customerId) {
        EventItem eventItem = eventService.getEventById(eventItemId);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (eventItem != null && customer != null) {
            // Remove existing rating
            Rating existingRating = ratingRepository.findByCustomerIdAndEventItemId(customerId, eventItemId);
            if (existingRating != null) {
                ratingRepository.delete(existingRating);
            }
            // Add new rating
            Rating rating = new Rating(ratingValue, eventItem, customer);
            ratingRepository.save(rating);

            //calculate average rating
            int sum = 0;
            int count = 0;
            for (Rating r : ratingRepository.findByEventItemId(eventItemId)) {
                sum += r.getRating();
                count++;
            }
            eventItem.setAvgRating(sum / count);
            eventService.updateEvent(eventItem);
            
            return rating;
        } else {
            return null;
        }
    }

    public int getRatingByCustomerAndEvent(long customerId, long eventItemId) {
        Rating rating = ratingRepository.findByCustomerIdAndEventItemId(customerId, eventItemId);
        return rating != null ? rating.getRating() : 0;
    }
}