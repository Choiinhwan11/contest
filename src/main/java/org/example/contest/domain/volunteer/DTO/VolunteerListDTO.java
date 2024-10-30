package org.example.contest.domain.volunteer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.contest.domain.volunteer.entity.Volunteer;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerListDTO {
    private Long id;
    private Long userId;
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

    public static VolunteerListDTO fromEntity(Volunteer volunteer) {
        return VolunteerListDTO.builder()
                .id(volunteer.getId())
                .province(volunteer.getProvince())
                .city(volunteer.getCity())
                .district(volunteer.getDistrict())
                .location(volunteer.getLocation())
                .title(volunteer.getTitle())
                .victimType(volunteer.getVictimType())
                .contactNumber(volunteer.getContactNumber())
                .date(volunteer.getDate())
                .startTime(volunteer.getStartTime())
                .endTime(volunteer.getEndTime())
                .volunteerCount(volunteer.getVolunteerCount())
                .preparation(volunteer.getPreparation())
                .description(volunteer.getDescription())
                .build();
    }

}
