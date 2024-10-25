package org.example.contest.domain.volunteer.service;

import org.example.contest.domain.volunteer.DTO.VolunteerCreateDTO;
import org.example.contest.domain.volunteer.DTO.VolunteerListDTO;
import org.example.contest.domain.volunteer.entity.Volunteer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VolunteerService {
    Volunteer createVolunteer(VolunteerCreateDTO volunteerCreateDTO);

    Page<VolunteerListDTO> getVolunteerAllList(int page, int size);

    Page<VolunteerListDTO> getVolunteerList(int page, int size, String province, String city, String district);

    boolean applyForVolunteer(Long volunteerId, Long userId);

    List<VolunteerListDTO> getMyVolunteerList(Long userId);


//    Page<VolunteerListDTO> getVolunteerList(int page, int size, String province, String city, String district);
}
