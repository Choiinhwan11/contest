package org.example.contest.domain.Report.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportRequest {
    // Getter 및 Setter 메소드
    private Double lat;
    private Double lng;
    private String message;

    // 기본 생성자
    public ReportRequest() {
    }

    // 파라미터가 있는 생성자
    public ReportRequest(Double lat, Double lng, String message) {
        this.lat = lat;
        this.lng = lng;
        this.message = message;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "ReportRequest{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", message='" + message + '\'' +
                '}';
    }
}
