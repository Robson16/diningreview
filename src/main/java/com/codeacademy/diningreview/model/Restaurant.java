package com.codeacademy.diningreview.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

    @NonNull
    @Column(name = "cuisine_type", nullable = false)
    private String cuisineType;

    @Column(name = "peanut_score", precision = 3, scale = 1)
    private BigDecimal peanutScore;

    @Column(name = "egg_score", precision = 3, scale = 1)
    private BigDecimal eggScore;

    @Column(name = "dairy_score", precision = 3, scale = 1)
    private BigDecimal dairyScore;

    @Column(name = "overall_score", precision = 3, scale = 1)
    private BigDecimal overallScore;
}
