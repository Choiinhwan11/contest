package org.example.contest.domain.naturaldisaster.service;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.config.VWCoordinateConverter;
import org.example.contest.domain.naturaldisaster.config.NaturaldisasterConfig;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NaturaldisasterServiceImpl implements NaturaldisasterService {

    private final NaturaldisasterConfig naturaldisasterConfig;

    @Override
    public Map<String, Object> getDisasterZones(double lat, double lng) {
        RestTemplate restTemplate = new RestTemplate();

        //좌표 변환
        ProjCoordinate transformedCoordinates = VWCoordinateConverter.useWGS84(lng, lat);
        System.out.println("변환된 좌표 (WGS84): " + transformedCoordinates.x + ", " + transformedCoordinates.y);

        // VWorld API 호출 URL 생성
        String VWurl = naturaldisasterConfig.getVWorldApiUrl() +
                "?service=data&request=GetFeature&data=LT_C_UP201" +
                "&key=" + naturaldisasterConfig.getVWorldApiKey() +
                "&geomFilter=POINT(" + transformedCoordinates.x + " " + transformedCoordinates.y + ")" +
                "&buffer=10000" +  // 반경 10km
                "&crs=EPSG:4326&format=json" +
                "&attrFilter=uname:LIKE:자연재해위험지구" +
                "&size=1000" +
                "&domain=http://localhost:8080";

        // URL 출력
        System.out.println("API 호출 URL: " + VWurl);

        try {

            ResponseEntity<Map> response = restTemplate.getForEntity(VWurl, Map.class);

            // 응답 데이터
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("API 호출 성공: " + response.getBody());
                Map<String, Object> responseData = response.getBody();

                // 데이터 필터링
                return Optional.ofNullable(responseData)
                        .map(this::filterDisasterData)
                        .orElseGet(HashMap::new);
            } else {
                System.out.println("API 호출 실패. 상태 코드: " + response.getStatusCode());
                throw new RuntimeException("VWorld API 호출 실패. 상태 코드: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("예외 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("VWorld API 데이터 가져오기 오류: " + e.getMessage());
        }
    }

    // 데이터를 필터링하는 (자연재해 추출)
    private Map<String, Object> filterDisasterData(Map<String, Object> data) {
        if (data == null || !data.containsKey("response")) {
            return new HashMap<>();
        }


        Map<String, Object> filteredData = new HashMap<>();

        // 응답 데이터가 포함되어 있는 경우 필터링 처리
        if (data.get("response") instanceof Map) {
            Map<String, Object> response = (Map<String, Object>) data.get("response");
            if (response.containsKey("result")) {
                Map<String, Object> result = (Map<String, Object>) response.get("result");

                if (result.containsKey("featureCollection")) {
                    Map<String, Object> featureCollection = (Map<String, Object>) result.get("featureCollection");

                    if (featureCollection.containsKey("features")) {
                        Iterable<Map<String, Object>> features = (Iterable<Map<String, Object>>) featureCollection.get("features");

                        List<Map<String, Object>> featureList = new ArrayList<>();
                        features.forEach(featureList::add);


                        List<Map<String, Object>> disasterFeatures = featureList.stream()
                                .filter(feature -> {
                                    if (feature.containsKey("properties")) {
                                        Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
                                        return properties.containsKey("uname") && properties.get("uname").equals("자연재해위험지구");
                                    }
                                    return false;
                                })
                                .collect(Collectors.toList());

                        //  저장
                        filteredData.put("filteredFeatures", disasterFeatures);
                    }
                }
            }
        }

        return filteredData;
    }
}
