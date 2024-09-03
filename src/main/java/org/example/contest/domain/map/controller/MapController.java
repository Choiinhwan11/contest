package org.example.contest.domain.map.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.map.service.MapService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "map")
public class MapController {

    private MapService mapService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/save")
    public String saveMap() {
        // 저장 로직
        return "Map saved";
    }
}
