package org.example.contest.domain.volunteer.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.volunteer.DTO.VolunteerApplyDTO;
import org.example.contest.domain.volunteer.DTO.VolunteerCreateDTO;
import org.example.contest.domain.volunteer.DTO.VolunteerListDTO;
import org.example.contest.domain.volunteer.entity.Volunteer;
import org.example.contest.domain.volunteer.repository.VolunteerApplicationRepository;
import org.example.contest.domain.volunteer.repository.VolunteerRepository;
import org.example.contest.domain.volunteer.service.VolunteerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping(path = "/volunteer")
public class VolunteerController {


    private final VolunteerService volunteerService;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerApplicationRepository volunteerApplicationRepository;


    /*
    * 봉사 등록
    * */
    @PostMapping(path = "/Registration")
    public ResponseEntity<String> createVolunteer(@RequestBody VolunteerCreateDTO volunteerCreateDTO) {
        try {
            volunteerService.createVolunteer(volunteerCreateDTO);
            return new ResponseEntity<>("Volunteer registration successful!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 봉사 목록 조회 (페이징 처리)
     */
    @GetMapping(path = "/list")
    public ResponseEntity<Page<VolunteerListDTO>> getVolunteerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district) {

        Page<VolunteerListDTO> volunteerList;
        if (province == null && city == null && district == null) {
            volunteerList = volunteerService.getVolunteerAllList(page, size);
        } else {
            volunteerList = volunteerService.getVolunteerList(page, size, province, city, district);
        }

        return ResponseEntity.ok(volunteerList);
    }

    /**
     * 디테일 페이지
     * */
    @GetMapping(path = "/detail/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        if (volunteer.isPresent()) {
            return ResponseEntity.ok(volunteer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/apply")
    public ResponseEntity<String> applyForVolunteer(@RequestBody VolunteerApplyDTO volunteerApplyDTO) {
        Long volunteerId = volunteerApplyDTO.getVolunteerId();
        Long userId = volunteerApplyDTO.getUserId();

        System.out.println("controller 에 들어옴 ");
        /*로직 */
        boolean isApplied = volunteerService.applyForVolunteer(volunteerId, userId);

        if (isApplied) {
            return ResponseEntity.ok("신청이 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("신청 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/myvolounteerlist")
    public List<VolunteerListDTO> getMyVolunteerList(@RequestParam("userId") Long userId) {
        System.out.println(userId);
        return volunteerService.getMyVolunteerList(userId);
    }

}
