package com.sameerasw.ticketin.server.repository;

import com.sameerasw.ticketin.server.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<Rating, Long> {

    int findRatingByEventItemId(long eventItemId);

    //get average rating of an event
    double findAvgRatingByEventItemId(long eventItemId);

    Rating findByCustomerIdAndEventItemId(long customerId, long eventItemId);
}
