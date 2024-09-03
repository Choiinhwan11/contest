//package org.example.contest.domain.weather.api;
//
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.io.BufferedReader;
//import java.io.IOException;
//
//public class WeatherAPIEx {
//    public static void main(String[] args) throws IOException {
//
//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");
//        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=서비스키");
//        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode("20210628", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode("0600", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55", "UTF-8"));
//        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127", "UTF-8"));
//
//        // URL 객체 생성
//        URL url = new URL(urlBuilder.toString());
//
//        // HTTP 연결 설정
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
//
//        // 응답 데이터 읽기
//        BufferedReader rd;
//        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//
//        // 응답을 StringBuilder로 저장
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//
//        // 리소스 해제
//        rd.close();
//        conn.disconnect();
//
//        // 응답 출력
//        System.out.println(sb.toString());
//    }
//}
