package org.example.contest.domain.volunteer.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolunteerApplyDTO {
    private Long volunteerId;
    private Long userId ;
}
