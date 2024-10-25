package org.example.contest.domain.interested.repository;

import org.example.contest.domain.interested.entity.InterestedInformation;
import org.example.contest.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterestedInformationRepository extends JpaRepository<InterestedInformation, Long> {




    Page<InterestedInformation> findByUserUserId(String userId, PageRequest pageRequest);

    /*List paging 처리  - > */


    @Query("SELECT ii FROM InterestedInformation ii WHERE ii.user.userId = :userId")
    Page<InterestedInformation> findByUser(User user, PageRequest pageRequest);
}
