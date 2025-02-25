# Dining Review API

This project is the final assignment for the **Create REST APIs with Spring and Java** course from [Codecademy](https://www.codecademy.com/).

## Overview
The **Dining Review API** is a RESTful web service that allows users to submit and review restaurants based on allergy-friendly criteria. The system enables users to rate restaurants based on peanut, egg, and dairy allergy-friendliness. An admin user is responsible for reviewing and approving submitted reviews.

## Features
- Users can submit dining reviews with optional scores for peanut, egg, and dairy allergies.
- Restaurants store the average scores for each allergy category and an overall score.
- Admins can approve or reject dining reviews.

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
- `submittedBy` (String) - User who submitted the review
- `restaurantId` (Long) - Reviewed restaurant
- `peanutScore` (Double) - Score (1-5) for peanut allergy safety
- `eggScore` (Double) - Score (1-5) for egg allergy safety
- `dairyScore` (Double) - Score (1-5) for dairy allergy safety
- `commentary` (String) - Optional comments

### Admin Review Action
- `accepted` (Boolean) - Indicates whether the review is accepted or rejected

## Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database** (for local persistence)
- **Lombok** (for reducing boilerplate code)

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

