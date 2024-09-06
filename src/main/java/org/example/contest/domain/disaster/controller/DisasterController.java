package org.example.contest.domain.disaster.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.disaster.service.DisasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "disaster")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DisasterController {

    public DisasterService disasterService;

    @GetMapping("/findDisaster")
    public ResponseEntity<String> getDisasterData() {
        // 서비스에서 재난 데이터를 호출
        String disasterData = disasterService.getDisasterData();

        // 200 반환
        if (disasterData != null) {
            return ResponseEntity.ok(disasterData);
        } else {
            //  500 에러
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve disaster data.");
        }
    }

}
