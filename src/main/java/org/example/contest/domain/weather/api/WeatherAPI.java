//package org.example.contest.domain.weather.api;
//
//
//import lombok.AllArgsConstructor;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//@AllArgsConstructor
//public class WeatherAPI {
//    private final String apiUrl;
//    private final String serviceKey;
//
//
//    public String getData(String endpoint, String[] params) throws Exception {
//        StringBuilder urlBuilder = new StringBuilder(apiUrl);
//        urlBuilder.append(endpoint);
//
//        for (String param : params) {
//            String[] keyValue = param.split("=");
//            String key = URLEncoder.encode(keyValue[0], StandardCharsets.UTF_8);
//            String value = URLEncoder.encode(keyValue[1], StandardCharsets.UTF_8);
//            urlBuilder.append("&").append(key).append("=").append(value);
//        }
//
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        int responseCode = conn.getResponseCode();
//        System.out.println("Response code: " + responseCode);
//
//        BufferedReader rd;
//        if (responseCode >= 200 && responseCode <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//
//        rd.close();
//        conn.disconnect();
//
//        return sb.toString();
//    }
//}
