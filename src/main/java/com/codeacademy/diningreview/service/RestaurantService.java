package com.codeacademy.diningreview.service;

import com.codeacademy.diningreview.dto.RestaurantResponse;
import com.codeacademy.diningreview.enums.ReviewStatus;
import com.codeacademy.diningreview.exception.AlreadyExistsException;
import com.codeacademy.diningreview.exception.NotFoundException;
import com.codeacademy.diningreview.model.DiningReview;
import com.codeacademy.diningreview.model.Restaurant;
import com.codeacademy.diningreview.repository.DiningReviewRepository;
import com.codeacademy.diningreview.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final DiningReviewRepository diningReviewRepository;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            DiningReviewRepository diningReviewRepository
    ) {
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
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
            throw new AlreadyExistsException("O restaurante já existe.");
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

    public List<RestaurantResponse> getRestaurantsByZipAndAllergy(String zipCode, boolean peanut, boolean egg, boolean dairy) {
        List<Restaurant> restaurants = this.restaurantRepository.findByZipCodeAndReviewedAllergies(zipCode, peanut, egg, dairy);
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

    public RestaurantResponse updateScores(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException("Restaurante não encontrado.");
        }

        Restaurant restaurant = restaurantOptional.get();

        List<DiningReview> approvedReviews = diningReviewRepository.findByRestaurantAndStatus(restaurant, ReviewStatus.APPROVED);

        double sumPeanut = 0, sumEgg = 0, sumDairy = 0;
        int countPeanut = 0, countEgg = 0, countDairy = 0;

        for (DiningReview review : approvedReviews) {
            if (review.getPeanutScore() != null) {
                sumPeanut += review.getPeanutScore();
                countPeanut++;
            }
            if (review.getEggScore() != null) {
                sumEgg += review.getEggScore();
                countEgg++;
            }
            if (review.getDairyScore() != null) {
                sumDairy += review.getDairyScore();
                countDairy++;
            }
        }

        double avgPeanutScore = countPeanut > 0 ? sumPeanut / countPeanut : 0;
        double avgEggScore = countEgg > 0 ? sumEgg / countEgg : 0;
        double avgDairyScore = countDairy > 0 ? sumDairy / countDairy : 0;
        double overallScore = (avgPeanutScore + avgEggScore + avgDairyScore) / 3;

        restaurant.setPeanutScore(avgPeanutScore);
        restaurant.setEggScore(avgEggScore);
        restaurant.setDairyScore(avgDairyScore);
        restaurant.setOverallScore(overallScore);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

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

}
