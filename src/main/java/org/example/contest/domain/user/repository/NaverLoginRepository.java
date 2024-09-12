package org.example.contest.domain.user.repository;

import org.example.contest.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaverLoginRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
