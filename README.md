# Dining Review API

This project is the final assignment for the **Create REST APIs with Spring and Java** course from [Codecademy](https://www.codecademy.com/).

## Overview
The **Dining Review API** is a RESTful web service that enables users to submit reviews for restaurants based on allergy-friendly criteria. Users can rate restaurants on a scale from 1 to 5 for peanut, egg, and dairy safety. Admins review and approve or reject these dining reviews. Additionally, the API recalculates and updates restaurant scores based on approved reviews.

## Features
- **User Reviews:** Users submit dining reviews with scores for peanut, egg, and dairy allergies, plus optional commentary.
- **Score Aggregation:** Restaurants store average scores for each allergy category as well as an overall score, calculated from approved reviews.
- **Admin Actions:** Admins can view pending reviews and update a review's status (approve or reject).
- **Flexible Endpoints:** Routes accept optional parameters (e.g., filtering dining reviews by status).
- **DTO-based Communication:** Both request and response data are managed through DTOs to decouple internal models from the API contract.
- **Global Exception Handling:** Errors and validation issues are managed centrally for clean and consistent API responses.

## Data Models

### Restaurant
- `id` (Long) - Unique identifier
- `name` (String) - Restaurant name
- `address` (String) - Location
- `cuisineType` (String) - Type of cuisine
- `peanutScore` (Double) - Average peanut allergy score
- `eggScore` (Double) - Average egg allergy score
- `dairyScore` (Double) - Average dairy allergy score
- `overallScore` (Double) - Overall average score

### User
- `id` (Long) - Unique identifier
- `displayName` (String) - Unique username
- `city` (String) - City of residence
- `state` (String) - State of residence
- `zipCode` (Long) - Zip code
- `interestedInPeanut` (Boolean) - Interest in peanut allergy ratings
- `interestedInEgg` (Boolean) - Interest in egg allergy ratings
- `interestedInDairy` (Boolean) - Interest in dairy allergy ratings

### DiningReview
- `id` (Long) - Unique identifier
- `submittedBy` (User) - User who submitted the review
- `restaurant` (Restaurant) - Reviewed restaurant
- `peanutScore` (Double) - Score (1-5) for peanut allergy safety
- `eggScore` (Double) - Score (1-5) for egg allergy safety
- `dairyScore` (Double) - Score (1-5) for dairy allergy safety
- `commentary` (String) - Optional comments
- `status` (ReviewStatus): Indicates whether the review is pending, approved or rejected.

### ReviewStatus (Enum)
- **PENDING**
- **APPROVED**
- **REJECTED**

## Endpoints Overview

### Users
- **POST /users:** Create a new user profile.
- **GET /users:** List of users.
- **GET /users/{displayName}:** Retrieve user profile(s).
- **PUT /users/{displayName}:** Update user data (displayName remains immutable).

### Restaurants
- **POST /restaurants:** Creates a new restaurant.
- **GET /restaurants:** List of restaurants.
- **GET /restaurants/{restaurantId}:** Details of the given restaurant.
- **GET /restaurants/search?zipCode=12345678&peanut=true&egg=true&dairy=true:** Search for restaurants by zip code and filter by allergy, if has scores calculated (one or more).
- **PUT /restaurants/{restaurantId}/update-scores:** Recalculate and update a restaurant’s scores based on approved dining reviews.

### Dining Reviews
- **POST /dining-reviews/{userDisplayName}/{restaurantId}:** Submit a new dining review.
- **PUT /dining-reviews/{id}/update-status/{newStatus}:** Update the status of a dining review.
- **GET /dining-reviews/{?status}:** Retrieve dining reviews, optionally filtered by status (PENDING, APPROVED, REJECTED).

## Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database** (for local persistence)
- **Lombok** (for reducing boilerplate code)
- **Global Exception Handling** with `@RestControllerAdvice` for consistent error responses

## Setup Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/Robson16/diningreview.git
   ```
2. Navigate to the project directory:
   ```sh
   cd diningreview
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## License
This project is licensed under the MIT License.

---
Feel free to contribute and enhance this project!

