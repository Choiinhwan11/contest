package org.example.contest.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.profile.DTO.ProfileInfoDTO;
import org.example.contest.domain.user.entity.User;
import org.example.contest.domain.user.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public ProfileInfoDTO getProfileInfoByUserid(String userId) {

        User user = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return ProfileInfoDTO.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .userPhone(String.valueOf(user.getPhone()))
                .userNickName(user.getUserId())
                .build();
    }
}
