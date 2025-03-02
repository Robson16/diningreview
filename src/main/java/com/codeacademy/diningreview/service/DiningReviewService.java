package com.codeacademy.diningreview.service;

import com.codeacademy.diningreview.dto.DiningReviewRequest;
import com.codeacademy.diningreview.dto.DiningReviewResponse;
import com.codeacademy.diningreview.enums.ReviewStatus;
import com.codeacademy.diningreview.exception.NotFoundException;
import com.codeacademy.diningreview.model.DiningReview;
import com.codeacademy.diningreview.model.Restaurant;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.repository.DiningReviewRepository;
import com.codeacademy.diningreview.repository.RestaurantRepository;
import com.codeacademy.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiningReviewService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DiningReviewRepository diningReviewRepository;

    public DiningReviewService(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            DiningReviewRepository diningReviewRepository
    ) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
    }

    public DiningReviewResponse createDiningReview(
            String userDisplayName,
            Long restaurantId,
            DiningReviewRequest diningReviewRequest
    ) {
        Optional<User> userOptional = this.userRepository.findByDisplayName(userDisplayName);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuario não encontrado.");
        }

        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new NotFoundException("Restaurante não encontrado.");
        }

        User user = userOptional.get();
        Restaurant restaurant = restaurantOptional.get();

        DiningReview diningReview = new DiningReview(
                null,
                user,
                restaurant,
                diningReviewRequest.peanutScore(),
                diningReviewRequest.eggScore(),
                diningReviewRequest.dairyScore(),
                diningReviewRequest.commentary(),
                ReviewStatus.PENDING
        );

        DiningReview savedDiningReview = this.diningReviewRepository.save(diningReview);

        return new DiningReviewResponse(
                savedDiningReview.getSubmittedBy().getDisplayName(),
                savedDiningReview.getRestaurant().getId(),
                savedDiningReview.getPeanutScore(),
                savedDiningReview.getEggScore(),
                savedDiningReview.getDairyScore(),
                savedDiningReview.getCommentary(),
                savedDiningReview.getStatus()
        );
    }
}
