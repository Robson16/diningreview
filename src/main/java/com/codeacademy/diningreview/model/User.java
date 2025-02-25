package com.codeacademy.diningreview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name", unique = true, nullable = false)
    private String displayName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "peanut_allergy_interest", nullable = false)
    private Boolean peanutAllergyInterest;

    @Column(name = "egg_allergy_interest", nullable = false)
    private Boolean eggAllergyInterest;

    @Column(name = "dairy_allergy_interest", nullable = false)
    private Boolean dairyAllergyInterest;
}
