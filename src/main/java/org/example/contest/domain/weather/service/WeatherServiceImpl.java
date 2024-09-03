package org.example.contest.domain.weather.service;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.weather.entity.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.serviceKey}")
    private String serviceKey;

    /**
     * 이 메서드는 기상청 API로부터 특정 좌표에 대한 날씨 데이터를 가져오는 기능을 합니다.
     *
     * @param nx - X 좌표 (격자 X)
     * @param ny - Y 좌표 (격자 Y)
     * @return 날씨 데이터를 포함한 JSONObject
     */
    @Override
    public JSONObject getWeatherData(String nx, String ny) {
        // 현재 날짜
        LocalDate today = LocalDate.now();

        // 예보 시간 설정
        List<String> forecastTimes = new ArrayList<>(List.of("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300"));
        forecastTimes.add("0000");

        //ㄱ가까운 시간
        LocalTime now = LocalTime.now();
        String baseTime = Weather.getClosestForecastTime(now, forecastTimes);
        System.out.println("baseTime = " + baseTime);

        //  날짜를 yyyyMMdd
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String baseDate = today.format(dateFormatter);
        System.out.println("baseDate = " + baseDate);

        try {
            // 서비스 키와 요청 파라미터를 설정합니다.
            //String serviceKeyEncoded = serviceKey;
            String serviceKeyEncoded = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8.toString());
            String numOfRows = "10";
            String pageNo = "1";
            String dataType = "JSON";
            String baseDateEncoded = URLEncoder.encode(baseDate, StandardCharsets.UTF_8.toString());
            String baseTimeEncoded = URLEncoder.encode(baseTime, StandardCharsets.UTF_8.toString());
            String nxEncoded = URLEncoder.encode(nx, StandardCharsets.UTF_8.toString());
            String nyEncoded = URLEncoder.encode(ny, StandardCharsets.UTF_8.toString());

            // StringBuilder
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?serviceKey=").append(serviceKeyEncoded);
            urlBuilder.append("&numOfRows=").append(numOfRows);
            urlBuilder.append("&pageNo=").append(pageNo);
            urlBuilder.append("&base_date=").append(baseDateEncoded);
            urlBuilder.append("&base_time=").append(baseTimeEncoded);
            urlBuilder.append("&nx=").append(nxEncoded);
            urlBuilder.append("&ny=").append(nyEncoded);
            urlBuilder.append("&dataType=").append(dataType);

            String url = urlBuilder.toString();

            System.out.println("url = " + url);
            System.out.println("base_Date = " + baseDate);
            System.out.println("base_Time = " + baseTime);
            System.out.println("Request URL: " + url);

            // API 요청을 위한 헤더를 설정합니다.
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept-Charset", "UTF-8");
            headers.set("Content-Type", "application/json");

            // HTTP 요청을 생성합니다.
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println("entity = " + entity);


            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                String xmlResponse = responseEntity.getBody();
                System.out.println("API Response: " + xmlResponse);

                if (xmlResponse != null && !xmlResponse.isEmpty()) {
                    // XML 응답을 JSON 객체로 변환합니다.
                    JSONObject jsonResponse = XML.toJSONObject(xmlResponse);
                    System.out.println("Converted JSON Response: " + jsonResponse.toString(2));

                    // 변환된 JSON 응답을 처리합니다.
                    return parseJsonResponse(jsonResponse);
                } else {
                    throw new RuntimeException("Empty or null response from weather API");
                }
            } catch (Exception e) {
                String errorMessage = String.format("Failed to retrieve weather data from URL: %s, with nx: %s, ny: %s. Error: %s", url, nx, ny, e.getMessage());
                System.err.println(errorMessage);
                throw new RuntimeException(errorMessage, e);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding error", e);
        }
    }

    /**
     * 이 메서드는 기상청 API의 JSON 응답을 파싱하여 날씨 데이터를 추출하는 기능을 합니다.
     *
     * @param jsonResponse - 기상청 API로부터 받은 JSON 응답
     * @return 파싱된 날씨 데이터를 포함한 JSONObject
     */
    private JSONObject parseJsonResponse(JSONObject jsonResponse) {
        try {
            System.out.println("Full JSON Response: " + jsonResponse.toString(2));  // 전체 응답 출력
            if (jsonResponse.has("response")) {  // "response" 필드가 존재하는지 확인
                JSONObject responseBody = jsonResponse.getJSONObject("response").getJSONObject("body");
                JSONArray itemArray = responseBody.getJSONObject("items").getJSONArray("item");

                if (itemArray.length() > 0) {
                    JSONObject firstItem = itemArray.getJSONObject(0);
                    double temperature = firstItem.optDouble("T1H", Double.NaN);  // 온도
                    double humidity = firstItem.optDouble("REH", Double.NaN);     // 습도
                    double precipitation = firstItem.optDouble("RN1", Double.NaN); // 강수량

                    JSONObject weatherData = new JSONObject();
                    weatherData.put("temperature", temperature);
                    weatherData.put("humidity", humidity);
                    weatherData.put("precipitation", precipitation);

                    return weatherData;
                } else {
                    throw new RuntimeException("No items found in the JSON response");
                }
            } else {
                String errorMsg = jsonResponse.optJSONObject("OpenAPI_ServiceResponse")
                        .optJSONObject("cmmMsgHeader")
                        .optString("errMsg", "Unknown error");
                throw new RuntimeException("API Error: " + errorMsg);
            }
        } catch (JSONException ex) {
            throw new RuntimeException("Invalid JSON structure", ex);
        }
    }

}

