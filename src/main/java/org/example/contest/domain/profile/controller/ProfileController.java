package org.example.contest.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.profile.DTO.ProfileInfoDTO;
import org.example.contest.domain.profile.DTO.ProfileInfoDTO;
import org.example.contest.domain.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(path = "/getProfile")
    public ResponseEntity<ProfileInfoDTO> getProfileInfo(@RequestParam("userId") String userId) {
        ProfileInfoDTO profileInfo = profileService.getProfileInfoByUserid(userId);
        return ResponseEntity.ok(profileInfo);
    }
}
