package org.example.contest.domain.naturaldisaster.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.naturaldisaster.service.NaturaldisasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/disaster")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NaturaldisasterController {

    private final NaturaldisasterService naturaldisasterService;

    // 클라이언트에서 lat, lng 받아서 좌표 변환 후 재해 구역 조회
    @GetMapping("/stay")
    public ResponseEntity<?> getDisasterZones(@RequestParam double lat, @RequestParam double lng) {
        try {
            Map<String, Object> response = naturaldisasterService.getDisasterZones(lat, lng);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching data from VWorld API: " + e.getMessage());
        }
    }
}
