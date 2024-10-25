package org.example.contest.domain.volunteer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VolunteerCreateDTO {
    private Long id;
    private String province;
    private String city;
    private String district;
    private String location;
    private String title;
    private String victimType;
    private String contactNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int volunteerCount;
    private String preparation;
    private String description;
    private int maxParticipants;
}

