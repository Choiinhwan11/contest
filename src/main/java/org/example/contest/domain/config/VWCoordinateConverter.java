package org.example.contest.domain.config;

import org.locationtech.proj4j.ProjCoordinate;

public class VWCoordinateConverter {

    // WGS84 좌표를 그대로 반환하는 메서드
    public static ProjCoordinate useWGS84(double x, double y) {
        // 입력 좌표를 그대로 반환 (변환 없이)
        System.out.println("입력 좌표 (WGS84 좌표계): x=" + x + ", y=" + y);
        return new ProjCoordinate(x, y);
    }
}
