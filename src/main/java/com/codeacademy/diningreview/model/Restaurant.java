package com.codeacademy.diningreview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "restaurant")
    private List<DiningReview> diningReviews;

    @NonNull
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @NonNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NonNull
    @Column(name = "cuisine_type", nullable = false)
    private String cuisineType;

    @Column(name = "peanut_score")
    private Double peanutScore;

    @Column(name = "egg_score")
    private Double eggScore;

    @Column(name = "dairy_score")
    private Double dairyScore;

    @Column(name = "overall_score")
    private Double overallScore;
}