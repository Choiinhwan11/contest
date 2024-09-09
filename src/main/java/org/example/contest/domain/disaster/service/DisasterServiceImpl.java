package org.example.contest.domain.disaster.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private String numOfRows = "10";

    @Override
    public String getDisasterData() {
        try {
            // API를 호출하기 위한 URL 생성
            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?dataName=").append(URLEncoder.encode(dataName, "UTF-8")); // 인코딩된 데이터명 추가
            urlBuilder.append("&serviceKey=").append(URLEncoder.encode(serviceKey, "UTF-8")); // 올바른 키명 사용
            urlBuilder.append("&pageNo=").append(URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&numOfRows=").append(URLEncoder.encode(numOfRows, "UTF-8"));

            // 완성된 URL 출력 (디버깅 용)
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

                return extractDisasterInfo(sb.toString());
            } else {
                // 응답 실패 처리
                System.err.println("API 호출 실패: 응답 코드 " + responseCode);
                connection.disconnect();
                return null;
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    private String extractDisasterInfo(String jsonResponse) {
        // JSON 응답 파싱
        JSONObject jsonObject = new JSONObject(jsonResponse);

        // 결과 데이터를 저장할 StringBuilder
        StringBuilder result = new StringBuilder();

        // "body" 필드가 존재하는지 확인
        if (jsonObject.has("body")) {
            // "body"는 배열로 되어 있으므로 이를 처리
            JSONArray bodyArray = jsonObject.getJSONArray("body");

            for (int i = 0; i < bodyArray.length(); i++) {
                JSONObject item = bodyArray.getJSONObject(i);

                // '호우' 카테고리인 경우만 추출
                if (item.getString("DST_SE_NM").equals("호우")) {
                    String dateTime = item.getString("CRT_DT");
                    String location = item.getString("RCPTN_RGN_NM");
                    String message = item.getString("MSG_CN");

                    result.append("날짜/시간: ").append(dateTime).append("\n");
                    result.append("위치: ").append(location).append("\n");
                    result.append("메시지 내용: ").append(message).append("\n\n");
                }
            }
        } else {
            return "body 필드가 응답에 존재하지 않습니다.";
        }

        if (result.length() == 0) {
            return "호우 관련 정보가 없습니다.";
        }

        return result.toString();
    }

}
