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
    public ResponseEntity<String> getWeather(@RequestParam(value = "nx", required = true) String nx,
                                             @RequestParam(value = "ny", required = true) String ny) {
        try {
            // 날씨 데이터를 가져옴
            JSONObject weatherData = weatherService.getWeatherData(nx, ny);

            // JSON 데이터를 문자열로 변환하여 반환
            return ResponseEntity.ok(weatherData.toString());
        } catch (Exception e) {
            // 에러 발생 시 에러 메시지를 문자열로 변환하여 반환
            return ResponseEntity.status(500).body(new JSONObject().put("error", "Error fetching weather data: " + e.getMessage()).toString());
        }
    }
}
