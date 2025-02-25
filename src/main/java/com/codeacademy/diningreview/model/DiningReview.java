package com.codeacademy.diningreview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "dining_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User submittedBy;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Min(1)
    @Max(5)
    @Column(name = "peanut_score")
    private Double peanutScore;

    @Min(1)
    @Max(5)
    @Column(name = "egg_score")
    private Double eggScore;

    @Min(1)
    @Max(5)
    @Column(name = "dairy_score")
    private Double dairyScore;

    @Column(name = "commentary")
    private String commentary;
}
