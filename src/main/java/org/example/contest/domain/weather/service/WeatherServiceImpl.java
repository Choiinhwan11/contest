package org.example.contest.domain.weather.service;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.weather.entity.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private String WeatherApiUrl;

    @Value("${weather.api.serviceKey}")
    private String WeatherServiceKey;


    /**
     * get NX NY
     * weather api get
     * base URL build
     * HttpURLConnection
     * */
    @Override
    public JSONObject getWeatherData(String nx, String ny) {
        // 날짜 및 시간 설정
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 예보 시간 설정
        List<String> forecastTimes = new ArrayList<>(List.of("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300", "0000"));
        String baseTime = getClosestForecastTime(now, forecastTimes);
        String baseDate = today.format(dateFormatter);

        try {
            // x, y 좌표를 int로 변환
            int nxInt = (int) Math.round(Double.parseDouble(nx));
            int nyInt = (int) Math.round(Double.parseDouble(ny));

            String serviceKeyEncoded = WeatherServiceKey;
            String numOfRows = "100";
            String pageNo = "1";
            String baseDateEncoded = URLEncoder.encode(baseDate, StandardCharsets.UTF_8.toString());
            String baseTimeEncoded = URLEncoder.encode(baseTime, StandardCharsets.UTF_8.toString());
            String nxEncoded = URLEncoder.encode(String.valueOf(nxInt), StandardCharsets.UTF_8.toString());
            String nyEncoded = URLEncoder.encode(String.valueOf(nyInt), StandardCharsets.UTF_8.toString());

            // API URL 생성
            StringBuilder urlBuilder = new StringBuilder(WeatherApiUrl);
            urlBuilder.append("?serviceKey=").append(serviceKeyEncoded);
            urlBuilder.append("&numOfRows=").append(numOfRows);
            urlBuilder.append("&pageNo=").append(pageNo);
            urlBuilder.append("&base_date=").append(baseDateEncoded);
            urlBuilder.append("&base_time=").append(baseTimeEncoded);
            urlBuilder.append("&nx=").append(nxEncoded);
            urlBuilder.append("&ny=").append(nyEncoded);

            String urlStr = urlBuilder.toString();
            System.out.println("Request URL: " + urlStr);

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseBody = response.toString();
                System.out.println("API Response Body: " + responseBody);

                // XML-> JSON으로 변환
                if (responseBody.trim().startsWith("<")) {
                    JSONObject jsonResponse = XML.toJSONObject(responseBody);
                    System.out.println("Converted JSON Response: " + jsonResponse.toString(2));
                    return parseJsonResponse(jsonResponse);
                } else if (responseBody.trim().startsWith("{")) {
                    // JSON
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    System.out.println("JSON Response: " + jsonResponse.toString(2));
                    return parseJsonResponse(jsonResponse);
                } else {
                    throw new RuntimeException("Unexpected response format: " + responseBody);
                }
            } else {
                throw new RuntimeException("Failed to retrieve weather data: Response Code " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during API request", e);
        }
    }

    //  처리
    private JSONObject parseJsonResponse(JSONObject jsonResponse) {
        try {
            System.out.println("Full JSON Response: " + jsonResponse.toString(2));


            if (!jsonResponse.has("response")) {
                throw new RuntimeException("API response does not contain a 'response' field");
            }

            JSONObject responseObj = jsonResponse.getJSONObject("response");

            // header 확인
            if (!responseObj.has("header")) {
                throw new RuntimeException("API response does not contain a 'header' field");
            }

            String resultCode = responseObj.getJSONObject("header").optString("resultCode", "");
            if (!"00".equals(resultCode)) {
                String resultMsg = responseObj.getJSONObject("header").optString("resultMsg", "API Error");
                throw new RuntimeException("API Error: " + resultMsg);
            }

            // body 확인
            if (!responseObj.has("body")) {
                throw new RuntimeException("API response does not contain a 'body' field");
            }

            JSONObject responseBody = responseObj.getJSONObject("body");
            JSONArray items = responseBody.getJSONObject("items").getJSONArray("item");

            // 결과를 저장할 JSON 객체
            JSONObject resultData = new JSONObject();
            JSONArray itemArray = new JSONArray();

            //카테고리별로 처리
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String category = item.optString("category", "");

                JSONObject resultItem = new JSONObject();

                switch (category) {
                    case "LGT":  // 낙뢰
                        resultItem.put("categoryName", "낙뢰");
                        resultItem.put("value", item.optDouble("fcstValue", 0) + " KA");
                        break;
                    case "RN1":  // 강수량
                        double rainfall = item.optDouble("fcstValue", 0.0);
                        String riskLevel = Weather.getRainfallRiskLevel(rainfall);
                        resultItem.put("categoryName", "1시간 강수량");
                        resultItem.put("value", rainfall + " mm");
                        resultItem.put("riskLevel", riskLevel);
                        break;
                    case "T1H":  // 온도
                        double temperature = item.optDouble("fcstValue", -999);
                        resultItem.put("categoryName", "기온");
                        resultItem.put("value", temperature + " °C");
                        break;
                    case "REH":  // 습도
                        double humidity = item.optDouble("fcstValue", -999);
                        resultItem.put("categoryName", "습도");
                        resultItem.put("value", humidity + " %");
                        break;
                    case "SKY":  // 하늘 상태
                        int skyCondition = item.optInt("fcstValue", 1);
                        String skyStatus = Weather.getSkyCondition(skyCondition);
                        resultItem.put("categoryName", "하늘 상태");
                        resultItem.put("value", skyStatus);
                        break;
                    default:
                        continue;
                }

                resultItem.put("baseDate", item.optString("baseDate", "정보 없음"));
                resultItem.put("baseTime", item.optString("baseTime", "정보 없음"));
                resultItem.put("fcstDate", item.optString("fcstDate", "정보 없음"));
                resultItem.put("fcstTime", item.optString("fcstTime", "정보 없음"));

                System.out.println("Processed item: " + resultItem.toString());
                itemArray.put(resultItem);
            }

            resultData.put("item", itemArray);
            return resultData;
        } catch (JSONException ex) {
            throw new RuntimeException("Invalid JSON structure", ex);
        }
    }




    //  가까운예보 시간 계산
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
