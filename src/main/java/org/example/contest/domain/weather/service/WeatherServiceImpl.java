package org.example.contest.domain.weather.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.serviceKey}")
    private String serviceKey;

    @Override
    public JSONObject getWeatherData(String nx, String ny) {
        // 날짜와 시간 설정
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 불변 리스트 대신 수정 가능한 리스트로 변경
        List<String> forecastTimes = new ArrayList<>(List.of("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300", "0000"));
        String baseTime = getClosestForecastTime(now, forecastTimes);
        String baseDate = today.format(dateFormatter);

        System.out.println("baseTime = " + baseTime);
        System.out.println("baseDate = " + baseDate);

        try {
            // nx와 ny를 정수형으로 변환
            int nxInt = (int) Math.round(Double.parseDouble(nx));
            int nyInt = (int) Math.round(Double.parseDouble(ny));

            String serviceKeyEncoded = serviceKey;
            String numOfRows = "10";
            String pageNo = "1";
            String baseDateEncoded = URLEncoder.encode(baseDate, StandardCharsets.UTF_8.toString());
            String baseTimeEncoded = URLEncoder.encode(baseTime, StandardCharsets.UTF_8.toString());
            String nxEncoded = URLEncoder.encode(String.valueOf(nxInt), StandardCharsets.UTF_8.toString());
            String nyEncoded = URLEncoder.encode(String.valueOf(nyInt), StandardCharsets.UTF_8.toString());

            // API URL 생성
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?serviceKey=").append(serviceKeyEncoded);
            urlBuilder.append("&numOfRows=").append(numOfRows);
            urlBuilder.append("&pageNo=").append(pageNo);
            urlBuilder.append("&base_date=").append(baseDateEncoded);
            urlBuilder.append("&base_time=").append(baseTimeEncoded);
            urlBuilder.append("&nx=").append(nxEncoded);
            urlBuilder.append("&ny=").append(nyEncoded);

            // dataType 파라미터 제거됨
            String urlStr = urlBuilder.toString();
            System.out.println("Request URL: " + urlStr);

            // URL 연결 및 요청 전송
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답인 경우
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 응답 본문 출력 확인
                String responseBody = response.toString();
                System.out.println("API Response Body: " + responseBody);

                if (responseBody.trim().startsWith("<")) {
                    // XML을 JSON으로 변환
                    JSONObject jsonResponse = XML.toJSONObject(responseBody);
                    System.out.println("Converted JSON Response: " + jsonResponse.toString(2));
                    return parseJsonResponse(jsonResponse);
                } else if (responseBody.trim().startsWith("{")) {
                    // 이미 JSON 형식일 경우 그대로 처리
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    return parseJsonResponse(jsonResponse);
                } else {
                    throw new RuntimeException("Unexpected response format: " + responseBody);
                }
            } else {
                throw new RuntimeException("Failed to retrieve weather data: Response Code " + responseCode);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding error", e);
        } catch (Exception e) {
            throw new RuntimeException("Error during API request", e);
        }
    }

    private JSONObject parseJsonResponse(JSONObject jsonResponse) {
        try {
            System.out.println("Full JSON Response: " + jsonResponse.toString(2));

            // resultCode 확인
            String resultCode = jsonResponse.getJSONObject("response").getJSONObject("header").getString("resultCode");
            if (!"00".equals(resultCode)) {
                String resultMsg = jsonResponse.getJSONObject("response").getJSONObject("header").getString("resultMsg");
                throw new RuntimeException("API Error: " + resultMsg);
            }

            // 결과를 저장할 JSON 객체
            JSONObject resultData = new JSONObject();

            // body 필드가 있는지 확인하고 처리
            if (jsonResponse.getJSONObject("response").has("body")) {
                JSONObject responseBody = jsonResponse.getJSONObject("response").getJSONObject("body");
                JSONArray itemArray = responseBody.getJSONObject("items").getJSONArray("item");

                // 각 예보 항목을 카테고리별로 처리
                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject item = itemArray.getJSONObject(i);
                    String category = item.getString("category");
                    String fcstTime = item.optString("fcstTime", "");  // 안전하게 fcstTime 값을 가져옴

                    // 필요한 데이터 추출
                    switch (category) {
                        case "T1H":  // 기온
                            resultData.put("temperature_" + fcstTime, item.getDouble("fcstValue"));
                            break;
                        case "PTY":  // 강수 형태
                            resultData.put("precipitation_type_" + fcstTime, item.getInt("fcstValue"));
                            break;
                        case "RN1":  // 시간당 강수량
                            resultData.put("precipitation_amount_" + fcstTime, item.getDouble("fcstValue"));
                            break;
                        case "REH":  // 습도
                            resultData.put("humidity_" + fcstTime, item.getInt("fcstValue"));
                            break;
                    }
                }

                return resultData;
            } else {
                throw new RuntimeException("API response does not contain a 'body' field");
            }
        } catch (JSONException ex) {
            throw new RuntimeException("Invalid JSON structure", ex);
        }
    }

    private String getClosestForecastTime(LocalTime now, List<String> forecastTimes) {
        String closestTime = forecastTimes.get(0);
        for (String time : forecastTimes) {
            if (now.isBefore(LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm")))) {
                break;
            }
            closestTime = time;
        }
        return closestTime;
    }
}
