package com.codeacademy.diningreview.repository;

import com.codeacademy.diningreview.enums.ReviewStatus;
import com.codeacademy.diningreview.model.DiningReview;
import com.codeacademy.diningreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findByStatus(ReviewStatus status);

    List<DiningReview> findByRestaurantAndStatus(Restaurant restaurant, ReviewStatus status);
}
