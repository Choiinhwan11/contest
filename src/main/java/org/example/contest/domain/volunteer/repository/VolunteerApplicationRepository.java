package org.example.contest.domain.volunteer.repository;

import org.example.contest.domain.volunteer.DTO.VolunteerListDTO;
import org.example.contest.domain.volunteer.entity.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Long> {


    List<VolunteerApplication> findByUserId(Long userId);

    @Query("SELECT new org.example.contest.domain.volunteer.DTO.VolunteerListDTO(v.id, u.id, v.province, v.city, v.district, v.location, v.title, v.victimType, v.contactNumber, v.date, v.startTime, v.endTime, v.volunteerCount, v.preparation, v.description) " +
            "FROM VolunteerApplication va " +
            "JOIN va.volunteer v " +
            "JOIN va.user u " +
            "WHERE u.id = :userId")
    List<VolunteerListDTO> findVolunteerListByUserId(@Param("userId") Long userId);

}
