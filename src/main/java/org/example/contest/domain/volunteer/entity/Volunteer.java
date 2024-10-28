package org.example.contest.domain.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.contest.domain.user.entity.User;

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

    @ManyToOne(fetch = FetchType.LAZY)  // User와 Many-to-One 관계 설정
    @JoinColumn(name = "user_id")  // 외래키 이름 설정
    private User user;  // 자원봉사자 또는 자원봉사를 등록한 사용자

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
}
