package org.example.contest.domain.weather.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherItem {

    // 발표일자 (yyyyMMdd)
    @JsonProperty("baseDate")
    private String baseDate;

    // 발표시각 (HHmm)
    @JsonProperty("baseTime")
    private String baseTime;

    // 자료구분코드 (카테고리)
    @JsonProperty("category")
    private String category;

    // 예보일자 (yyyyMMdd)
    @JsonProperty("fcstDate")
    private String fcstDate;

    // 예보시간 (HHmm)
    @JsonProperty("fcstTime")
    private String fcstTime;


    @JsonProperty("fcstValue")
    private String fcstValue;

    // X좌표 (격자)
    @JsonProperty("nx")
    private int nx;

    // Y좌표 (격자)
    @JsonProperty("ny")
    private int ny;

    // 강수형태 (PTY), 0: 없음, 1: 비, 2: 비/눈, 3: 눈, 4: 소나기 등
    public boolean isPrecipitation() {
        return "PTY".equals(category);
    }

    // 기온 (T1H),
    public boolean isTemperature() {
        return "T1H".equals(category);
    }

    // 습도 (REH), %
    public boolean isHumidity() {
        return "REH".equals(category);
    }

    // 강수량 (RN1), 시간당 강수량 (1mm 단위)
    public boolean isRainfall() {
        return "RN1".equals(category);
    }

    // 낙뢰 (LGT), 0: 없음, 1: 있음
    public boolean isLightning() {
        return "LGT".equals(category);
    }
}
