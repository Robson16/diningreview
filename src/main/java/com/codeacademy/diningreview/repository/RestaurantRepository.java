package com.codeacademy.diningreview.repository;

import com.codeacademy.diningreview.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    boolean existsByNameAndZipCode(String name, String zipCode);
}
