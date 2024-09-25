package org.example.contest.domain.profile.service;

import org.example.contest.domain.profile.DTO.ProfileInfoDTO;
import org.springframework.data.jpa.repository.Query;

public interface ProfileService {


    @Query
    ProfileInfoDTO getProfileInfoByUserid(String userId);
}
