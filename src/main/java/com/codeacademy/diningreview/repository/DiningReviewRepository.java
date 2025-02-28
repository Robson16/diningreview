package com.codeacademy.diningreview.repository;

import com.codeacademy.diningreview.model.DiningReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
}
