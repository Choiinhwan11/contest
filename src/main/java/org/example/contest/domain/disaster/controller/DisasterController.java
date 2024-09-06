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

    public final DisasterService disasterService;



    @GetMapping("/getDisasterData")
    public ResponseEntity<String> getDisasterData() {
        String data = disasterService.getDisasterData();
        if (data != null) {
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.status(500).body("Error fetching disaster data");
        }
    }

}
