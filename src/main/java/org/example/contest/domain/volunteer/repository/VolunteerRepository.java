package org.example.contest.domain.volunteer.repository;

import org.example.contest.domain.volunteer.DTO.VolunteerListDTO;
import org.example.contest.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long>, JpaSpecificationExecutor<Volunteer> {

    // 특정 자원봉사에 특정 사용자가 이미 신청했는지 여부 확인
    @Query("SELECT CASE WHEN COUNT(va) > 0 THEN true ELSE false END " +
            "FROM VolunteerApplication va WHERE va.volunteer.id = :volunteerId " +
            "AND va.user.id = :userId")
    boolean existsByVolunteerIdAndUserId(@Param("volunteerId") Long volunteerId, @Param("userId") Long userId);

    // 특정 자원봉사에 신청된 총 인원 수 조회
    @Query("SELECT COUNT(va) FROM VolunteerApplication va WHERE va.volunteer.id = :volunteerId")
    int countByVolunteerId(@Param("volunteerId") Long volunteerId);

    // 특정 자원봉사와 사용자 ID로 자원봉사 조회
    @Query("SELECT v FROM Volunteer v JOIN VolunteerApplication va ON va.volunteer.id = v.id " +
            "WHERE v.id = :volunteerId AND va.user.id = :userId")
    Optional<Volunteer> findByVolunteerIdAndUserId(@Param("volunteerId") Long volunteerId, @Param("userId") Long userId);

    // 특정 사용자 ID로 사용자가 참여한 자원봉사 목록 조회
    @Query("SELECT (" +
            "v.id, v.province, v.city, v.district, v.location, v.title, v.victimType, " +
            "v.contactNumber, v.date, v.startTime, v.endTime, v.volunteerCount, v.preparation, v.description) " +
            "FROM VolunteerApplication va JOIN va.volunteer v " +
            "WHERE va.user.id = :userId")
    List<VolunteerListDTO> findByUserId(@Param("userId") Long userId);

}
