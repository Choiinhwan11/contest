package org.example.contest.domain.interested.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.interested.DTO.InterestDTO;
import org.example.contest.domain.interested.DTO.InterestListDTO;
import org.example.contest.domain.interested.entity.InterestedInformation;
import org.example.contest.domain.interested.repository.InterestedInformationRepository;
import org.example.contest.domain.user.entity.User;
import org.example.contest.domain.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestedServiceImpl implements InterestedService {

    private final InterestedInformationRepository interestedInformationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveInterest(InterestDTO interestDTO, String userId) {
        // userId를 통해 User 엔티티를 조회
        User user = (User) userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // InterestedInformation 엔티티 생성 및 저장
        InterestedInformation interest = InterestedInformation.builder()
                .province(interestDTO.getProvince())
                .city(interestDTO.getCity())
                .district(interestDTO.getDistrict())
                .relation(interestDTO.getRelation())
                .comment(interestDTO.getComment())
                .user(user)
                .build();

        interestedInformationRepository.save(interest);
    }




    public Page<InterestedInformation> getInterestListByUserId(String userId, PageRequest pageRequest) {
        // userId를 통해 User 엔티티를 조회
        User user = (User) userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // User 객체를 사용해 InterestedInformation 리스트를 페이징 처리하여 반환
        return interestedInformationRepository.findByUser(user, pageRequest);
    }

    public boolean checkIfUserExists(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }


}
