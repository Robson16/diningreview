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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<DiningReviewResponse> getAllDiningReview() {
        List<DiningReview> diningReviews = (List<DiningReview>) this.diningReviewRepository.findAll();
        return diningReviews.stream().map(
                        diningReview -> new DiningReviewResponse(
                                diningReview.getId(),
                                diningReview.getSubmittedBy().getDisplayName(),
                                diningReview.getRestaurant().getId(),
                                diningReview.getPeanutScore(),
                                diningReview.getEggScore(),
                                diningReview.getDairyScore(),
                                diningReview.getCommentary(),
                                diningReview.getStatus()
                        ))
                .collect(Collectors.toList());
    }

    public List<DiningReviewResponse> getAllDiningReviewByStatus(ReviewStatus status) {
        List<DiningReview> diningReviews = this.diningReviewRepository.findByStatus(status);
        return diningReviews.stream().map(
                        diningReview -> new DiningReviewResponse(
                                diningReview.getId(),
                                diningReview.getSubmittedBy().getDisplayName(),
                                diningReview.getRestaurant().getId(),
                                diningReview.getPeanutScore(),
                                diningReview.getEggScore(),
                                diningReview.getDairyScore(),
                                diningReview.getCommentary(),
                                diningReview.getStatus()
                        ))
                .collect(Collectors.toList());
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
                savedDiningReview.getId(),
                savedDiningReview.getSubmittedBy().getDisplayName(),
                savedDiningReview.getRestaurant().getId(),
                savedDiningReview.getPeanutScore(),
                savedDiningReview.getEggScore(),
                savedDiningReview.getDairyScore(),
                savedDiningReview.getCommentary(),
                savedDiningReview.getStatus()
        );
    }

    public DiningReviewResponse updateStatus(Long id, ReviewStatus newStatus) {
        Optional<DiningReview> diningReviewOptional = this.diningReviewRepository.findById(id);
        if (diningReviewOptional.isEmpty()) {
            throw new NotFoundException("Dining Review não encontrado.");
        }

        DiningReview diningReview = diningReviewOptional.get();

        diningReview.setStatus(newStatus);

        DiningReview savedDiningReview = this.diningReviewRepository.save(diningReview);

        return new DiningReviewResponse(
                savedDiningReview.getId(),
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
