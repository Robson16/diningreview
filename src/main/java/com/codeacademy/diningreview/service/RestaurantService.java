package com.codeacademy.diningreview.service;

import com.codeacademy.diningreview.dto.RestaurantResponse;
import com.codeacademy.diningreview.exception.NotFoundException;
import com.codeacademy.diningreview.exception.RestaurantAlreadyExistsException;
import com.codeacademy.diningreview.model.Restaurant;
import com.codeacademy.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantResponse> getAllRestaurants() {
        List<Restaurant> restaurants = (List<Restaurant>) this.restaurantRepository.findAll();
        return restaurants.stream().map(
                        restaurant -> new RestaurantResponse(
                                restaurant.getId(),
                                restaurant.getName(),
                                restaurant.getAddress(),
                                restaurant.getZipCode(),
                                restaurant.getCuisineType(),
                                restaurant.getPeanutScore(),
                                restaurant.getEggScore(),
                                restaurant.getDairyScore(),
                                restaurant.getOverallScore()
                        ))
                .collect(Collectors.toList());
    }

    public RestaurantResponse createRestaurant(Restaurant restaurant) {
        if (this.restaurantRepository.existsByNameAndZipCode(restaurant.getName(), restaurant.getZipCode())) {
            throw new RestaurantAlreadyExistsException("O restaurante já existe.");
        }
        Restaurant savedRestaurant = this.restaurantRepository.save(restaurant);

        return new RestaurantResponse(
                savedRestaurant.getId(),
                savedRestaurant.getName(),
                savedRestaurant.getAddress(),
                savedRestaurant.getZipCode(),
                savedRestaurant.getCuisineType(),
                savedRestaurant.getPeanutScore(),
                savedRestaurant.getEggScore(),
                savedRestaurant.getDairyScore(),
                savedRestaurant.getOverallScore()
        );
    }

    public RestaurantResponse getRestaurantById(Long id) {
        Optional<Restaurant> restaurantToFindOptional = this.restaurantRepository.findById(id);

        if (restaurantToFindOptional.isEmpty()) {
            throw new NotFoundException("Restaurante não encontrado.");
        }

        Restaurant restaurantFound = restaurantToFindOptional.get();

        return new RestaurantResponse(
                restaurantFound.getId(),
                restaurantFound.getName(),
                restaurantFound.getAddress(),
                restaurantFound.getZipCode(),
                restaurantFound.getCuisineType(),
                restaurantFound.getPeanutScore(),
                restaurantFound.getEggScore(),
                restaurantFound.getDairyScore(),
                restaurantFound.getOverallScore()
        );
    }
}
