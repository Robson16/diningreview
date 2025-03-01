package com.codeacademy.diningreview.repository;

import com.codeacademy.diningreview.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    boolean existsByNameAndZipCode(String name, String zipCode);

    @Query("SELECT DISTINCT r " +
            "FROM Restaurant r " +
            "JOIN r.diningReviews dr " +
            "WHERE r.zipCode = :zipCode " +
            "AND dr.status = 'APPROVED' " +
            "AND (" +
            "(:peanut = TRUE AND dr.peanutScore IS NOT NULL) OR " +
            "(:egg = TRUE AND dr.eggScore IS NOT NULL) OR " +
            "(:dairy = TRUE AND dr.dairyScore IS NOT NULL)) " +
            "ORDER BY COALESCE(r.overallScore, 0) DESC")
    List<Restaurant> findByZipCodeAndReviewedAllergies(
            @Param("zipCode") String zipCode,
            @Param("peanut") boolean peanut,
            @Param("egg") boolean egg,
            @Param("dairy") boolean dairy
    );
}