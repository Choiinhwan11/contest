package org.example.contest.domain.volunteer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.contest.domain.user.entity.User;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolunteerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;  // 신청한 자원봉사 정보


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VolunteerApplication(Volunteer volunteer, Long userId) {
    }


}
