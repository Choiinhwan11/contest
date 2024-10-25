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
                .id(volunteer.getId())  // 자원봉사 ID
                .province(volunteer.getProvince())  // 도
                .city(volunteer.getCity())  // 시
                .district(volunteer.getDistrict())  // 구
                .location(volunteer.getLocation())  // 자원봉사 위치
                .title(volunteer.getTitle())  // 자원봉사 제목
                .victimType(volunteer.getVictimType())  // 대상자 유형
                .contactNumber(volunteer.getContactNumber())  // 연락처
                .date(volunteer.getDate())  // 자원봉사 날짜
                .startTime(volunteer.getStartTime())  // 시작 시간
                .endTime(volunteer.getEndTime())  // 종료 시간
                .volunteerCount(volunteer.getVolunteerCount())  // 현재 자원봉사자 수
                .preparation(volunteer.getPreparation())  // 준비물
                .description(volunteer.getDescription())  // 자원봉사 설명
                .build();
    }

}
