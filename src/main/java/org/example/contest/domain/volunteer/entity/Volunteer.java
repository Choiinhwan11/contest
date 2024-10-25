package org.example.contest.domain.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String victimType;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int volunteerCount;

    @Column(nullable = false, length = 1000)
    private String preparation;

    @Column(nullable = false, length = 2000)
    private String description;

    private int maxParticipants;

//    private Volunteer(Builder builder) {
//        this.id = builder.id;
//        this.province = builder.province;
//        this.city = builder.city;
//        this.district = builder.district;
//        this.location = builder.location;
//        this.title = builder.title;
//        this.victimType = builder.victimType;
//        this.contactNumber = builder.contactNumber;
//        this.date = builder.date;
//        this.startTime = builder.startTime;
//        this.endTime = builder.endTime;
//        this.volunteerCount = builder.volunteerCount;
//        this.preparation = builder.preparation;
//        this.description = builder.description;
//    }
//
//
//    // Static inner Builder class
//    public static class Builder {
//        private Long id;
//        private String province;
//        private String city;
//        private String district;
//        private String location;
//        private String title;
//        private String victimType;
//        private String contactNumber;
//        private LocalDate date;
//        private LocalTime startTime;
//        private LocalTime endTime;
//        private int volunteerCount;
//        private String preparation;
//        private String description;
//
//    }
}