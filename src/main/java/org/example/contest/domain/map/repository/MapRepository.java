package org.example.contest.domain.map.repository;

import org.example.contest.domain.map.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface MapRepository extends JpaRepository<MapEntity, Long> {
}
