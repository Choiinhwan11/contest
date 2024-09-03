package org.example.contest.domain.weather.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.weather.service.WeatherService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    private final WeatherService weatherService;


    @GetMapping("/getWeather")
    public ResponseEntity<JSONObject> getWeather(@RequestParam(value = "nx", required = true) String nx,
                                                 @RequestParam(value = "ny", required = true) String ny) {

        JSONObject weatherData = weatherService.getWeatherData(nx, ny);
        return ResponseEntity.ok(weatherData);
    }
}
