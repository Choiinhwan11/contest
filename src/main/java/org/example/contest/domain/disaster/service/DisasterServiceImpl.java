package org.example.contest.domain.disaster.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class DisasterServiceImpl implements DisasterService {

    @Value("${disaster.api.url}")
    private String apiUrl;

    @Value("${disaster.api.serviceKey}")
    private String serviceKey;

    private String dataName = "데이터명";
    private String pageNo = "1";
    private String numOfRows = "100";

    @Override
    public String getDisasterData() {
        try {
            // API를 호출하기 위한 URL 생성
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append(URLEncoder.encode(dataName, "UTF-8"));
            urlBuilder.append("?" + "servicekey=" + URLEncoder.encode(serviceKey, "UTF-8"));
            urlBuilder.append("&" + "pageNo=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + "numOfRows=" + URLEncoder.encode(numOfRows, "UTF-8"));

            System.out.println("Request URL: " + urlBuilder.toString());

            // URI와 URL 생성
            URI uri = new URI(urlBuilder.toString());
            URL url = uri.toURL();

            // HTTP 커넥션 생성
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                // 정상 응답일 경우 데이터를 읽음
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                connection.disconnect();

                // API 응답 반환
                return sb.toString();
            } else {
                System.err.println("API 호출 실패: 응답 코드 " + responseCode);
                connection.disconnect();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
