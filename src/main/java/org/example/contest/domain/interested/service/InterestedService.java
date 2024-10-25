package org.example.contest.domain.interested.service;

import org.example.contest.domain.interested.DTO.InterestDTO;
import org.example.contest.domain.interested.entity.InterestedInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface InterestedService {


    void saveInterest(InterestDTO interestDTO, String userId);


    Page<InterestedInformation> getInterestListByUserId(String userId, PageRequest pageRequest);

    boolean checkIfUserExists(String userId);
}
