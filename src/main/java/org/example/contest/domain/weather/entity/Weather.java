package org.example.contest.domain.weather.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String obsrValue;
    private String baseDate;
    private String baseTime;
    private String nx;
    private String ny;


    /**
     * 시간 서비스 가까운 시간에 맞춰주기
     * */
    public static String getClosestForecastTime(LocalTime now, List<String> forecastTimes) {
        // 리스트가 비어있는지 확인
        if (forecastTimes == null || forecastTimes.isEmpty()) {
            throw new IllegalArgumentException("Forecast times list cannot be null or empty");
        }
        // 리스트 정렬
        Collections.sort(forecastTimes);

        String currentFormattedTime = now.format(DateTimeFormatter.ofPattern("HHmm"));
        for (int i = forecastTimes.size() - 1; i >= 0; i--) {
            if (currentFormattedTime.compareTo(forecastTimes.get(i)) >= 0) {
                return forecastTimes.get(i);
            }
        }
        //  전날 마지막 예보 시각을 반환
        return forecastTimes.get(forecastTimes.size() - 1);
    }

}
