package org.example.contest.domain.map.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.map.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {

    private final LocationService locationService;


    @GetMapping("/city")
    public ResponseEntity<String> getCityFromCoords(
            @RequestParam String coords,
            @RequestParam String output,
            @RequestParam String orders
    ) {
        try {
            String[] splitCoords = coords.split(",");
            if (splitCoords.length != 2) {
                return ResponseEntity.badRequest().body("Invalid coordinates format");
            }

            String longitude = splitCoords[0];
            String latitude = splitCoords[1];

            // 서비스 호출
            String city = locationService.getCityFromLocation(latitude, longitude);
            return ResponseEntity.ok(city);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching city information: " + e.getMessage());
        }
    }
}
