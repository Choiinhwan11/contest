package org.example.contest.domain.volunteer.DTO;

import org.example.contest.domain.volunteer.entity.Volunteer;

public class VolunteerConverterDTO {


    public VolunteerListDTO convertToDTO(Volunteer volunteer) {
        return VolunteerListDTO.builder()
                .id(volunteer.getId())  // 자원봉사 리스트 아이디
                .userId(volunteer.getUser().getId()) //회원 아이디
                .province(volunteer.getProvince())  // 도
                .city(volunteer.getCity())  // 시
                .district(volunteer.getDistrict())  // 구
                .location(volunteer.getLocation())  // 구체적인 위치
                .title(volunteer.getTitle())  // 자원봉사 제목
                .victimType(volunteer.getVictimType())  // 대상자 유형
                .contactNumber(volunteer.getContactNumber())  // 연락처
                .date(volunteer.getDate())  // 자원봉사 날짜
                .startTime(volunteer.getStartTime())  // 자원봉사 시작 시간
                .endTime(volunteer.getEndTime())  // 자원봉사 종료 시간
                .volunteerCount(volunteer.getVolunteerCount())  // 현재 신청 인원
                .preparation(volunteer.getPreparation())  // 준비물
                .description(volunteer.getDescription())  // 자원봉사 설명
                .build();
    }
}
