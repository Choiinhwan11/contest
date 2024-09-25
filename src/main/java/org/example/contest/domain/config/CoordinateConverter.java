package org.example.contest.domain.config;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

public class CoordinateConverter {

    // EPSG:5179 -> WGS84로 변환하는 메서드
    public static ProjCoordinate convertTMtoWGS84(double x, double y) {
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CRSFactory crsFactory = new CRSFactory();

        // EPSG:5179 (한국 TM 좌표계) -> WGS84 (위도/경도)로 변환
        CoordinateReferenceSystem tm = crsFactory.createFromName("EPSG:5179"); // 한국 TM 좌표계
        CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326"); // WGS84 좌표계

        CoordinateTransform transform = ctFactory.createTransform(tm, wgs84);
        ProjCoordinate srcCoord = new ProjCoordinate(x, y);
        ProjCoordinate destCoord = new ProjCoordinate();

        transform.transform(srcCoord, destCoord);
        return destCoord; // 변환된 위도(lat)와 경도(lon)
    }
}
