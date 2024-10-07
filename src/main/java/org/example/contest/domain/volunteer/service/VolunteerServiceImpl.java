package org.example.contest.domain.volunteer.serivce;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.volunteer.entity.Volunteer;
import org.example.contest.domain.volunteer.repository.VolunteerRepository;
import org.example.contest.domain.volunteer.serivce.VolunteerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    /**
     * 봉사 등록 메서드
     *
     * @param volunteer 봉사활동 엔티티
     * @return 저장된 봉사활동 엔티티
     */
    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }
}
