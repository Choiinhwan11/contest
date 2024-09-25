package org.example.contest.domain.user.repository;

import org.example.contest.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
}
