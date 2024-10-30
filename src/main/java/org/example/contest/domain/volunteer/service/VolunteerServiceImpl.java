package org.example.contest.domain.volunteer.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.volunteer.DTO.VolunteerConverterDTO;
import org.example.contest.domain.volunteer.DTO.VolunteerCreateDTO;
import org.example.contest.domain.volunteer.DTO.VolunteerListDTO;
import org.example.contest.domain.volunteer.entity.Volunteer;
import org.example.contest.domain.volunteer.entity.VolunteerApplication;
import org.example.contest.domain.volunteer.repository.VolunteerApplicationRepository;
import org.example.contest.domain.volunteer.repository.VolunteerRepository;
import org.example.contest.domain.volunteer.service.VolunteerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerApplicationRepository volunteerApplicationRepository;

    /**
     * volounteer create
     * 봉사 등록
     *
     * */
    @Override
    public Volunteer createVolunteer(VolunteerCreateDTO volunteerCreateDTO) {
        Volunteer volunteer = Volunteer.builder()
                .province(volunteerCreateDTO.getProvince())
                .city(volunteerCreateDTO.getCity())
                .district(volunteerCreateDTO.getDistrict())
                .location(volunteerCreateDTO.getLocation())
                .title(volunteerCreateDTO.getTitle())
                .victimType(volunteerCreateDTO.getVictimType())
                .contactNumber(volunteerCreateDTO.getContactNumber())
                .date(volunteerCreateDTO.getDate())
                .startTime(volunteerCreateDTO.getStartTime())
                .endTime(volunteerCreateDTO.getEndTime())
                .volunteerCount(volunteerCreateDTO.getVolunteerCount())
                .preparation(volunteerCreateDTO.getPreparation())
                .description(volunteerCreateDTO.getDescription())
                .maxParticipants(volunteerCreateDTO.getMaxParticipants())
                .id(volunteerCreateDTO.getId())
                .build();

        return volunteerRepository.save(volunteer);
    }


    /**
     * volounteer list sort desc id
     **/
    @Override
    public Page<VolunteerListDTO> getVolunteerAllList(int page, int size) {
        Page<Volunteer> volunteers = volunteerRepository.findAll(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))
        );

        return volunteers.map(volunteer -> VolunteerListDTO.builder()
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
                .build()
        );
    }
/**
 * volounteer lsit paging and
 * */
    @Override
    public Page<VolunteerListDTO> getVolunteerList(int page, int size, String province, String city, String district) {
        if (province == null && city == null && district == null) {
            return getVolunteerAllList(page, size);
        } else {
            Specification<Volunteer> specification = (root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (province != null) {
                    predicates.add(criteriaBuilder.equal(root.get("province"), province));
                }
                if (city != null) {
                    predicates.add(criteriaBuilder.equal(root.get("city"), city));
                }
                if (district != null) {
                    predicates.add(criteriaBuilder.equal(root.get("district"), district));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            };

            Page<Volunteer> volunteers = volunteerRepository.findAll(
                    specification, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"))
            );

            return volunteers.map(volunteer -> VolunteerListDTO.builder()
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
                    .build()
            );
        }
    }

    @Override
    public boolean applyForVolunteer(Long volunteerId, Long userId) {
        // 1. 자원봉사 정보 찾기
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(volunteerId);
        if (optionalVolunteer.isEmpty()) {
            System.out.println("해당 자원봉사를 찾을 수 없습니다.");
            return false;
        }

        Volunteer volunteer = optionalVolunteer.get();

        // 2. 중복 신청 방지
        boolean isAlreadyApplied = volunteerRepository.existsByVolunteerIdAndUserId(volunteerId, userId);
        if (isAlreadyApplied) {
            System.out.println("해당 자원봉사에 이미 신청한 사용자입니다.");
            return false;
        }

        // 3. 최대 인원 초과 확인
        int currentCount = volunteerRepository.countByVolunteerId(volunteerId);
        if (currentCount >= volunteer.getMaxParticipants()) {
            System.out.println("해당 자원봉사의 최대 참여 인원이 초과되었습니다.");
            return false;
        }

        // 4. 신청 저장
        VolunteerApplication newApplication = new VolunteerApplication(volunteer, userId);
        volunteerApplicationRepository.save(newApplication);

        System.out.println("자원봉사 신청이 성공적으로 저장되었습니다.");
        return true;
    }

    /**
     * 봉사 리스트
     * userId 로 list 찾기
     * */
    @Override
    public List<VolunteerListDTO> getMyVolunteerList(Long userId) {
        try {
            // VolunteerApplicationRepository를 통해 userId로 필터링된 신청 목록을 조회
            List<VolunteerApplication> applications = volunteerApplicationRepository.findByUserId(userId);
            System.out.println("Retrieved Applications Count: " + applications.size());

            // 각 신청 내의 봉사(Volunteer)를 VolunteerListDTO로 변환하여 반환
            return applications.stream()
                    .map(application -> {
                        Volunteer volunteer = application.getVolunteer();
                        if (volunteer == null) {
                            System.out.println("Volunteer is null for application ID: " + application.getId());
                            return null;
                        }
                        System.out.println("Application ID: " + application.getId());
                        System.out.println("Volunteer ID: " + volunteer.getId());
                        System.out.println("Volunteer Title: " + volunteer.getTitle());
                        System.out.println("Volunteer Location: " + volunteer.getLocation());
                        System.out.println("Volunteer Date: " + volunteer.getDate());

                        // DTO 변환 후 출력
                        VolunteerListDTO dto = VolunteerListDTO.builder()
                                .id(volunteer.getId())
                                .userId(volunteer.getUser().getId())
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

                        System.out.println("Converted DTO: " + dto);
                        return dto;
                    })
                    .filter(dto -> dto != null) // null 값은 필터링
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
            return new ArrayList<>(); // 에러 발생 시 빈 리스트 반환
        }
    }


}
