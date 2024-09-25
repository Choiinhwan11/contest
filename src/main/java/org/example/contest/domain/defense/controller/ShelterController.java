package org.example.contest.domain.defense.controller;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.contest.domain.defense.dto.ShelterDTO;
import org.example.contest.domain.config.CoordinateConverter;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/shelters")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ShelterController {

    @GetMapping("/filtered")
    public List<ShelterDTO> getFilteredShelters(@RequestParam String city, @RequestParam String province) throws Exception {

        // 민방위 시설 Excel 파일의 URL
        String fileUrl = "https://www.localdata.go.kr/datafile/etc/LOCALDATA_ALL_12_04_12_E.xlsx";

        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed Excel file : " + responseCode);
        }

        try (InputStream inputStream = connection.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<ShelterDTO> shelters = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                String name = getCellValueAsString(row.getCell(5));
                String address = getCellValueAsString(row.getCell(7));

                // 주소에 시, 도 필터 적용
                if (!address.contains(city) || !address.contains(province)) {
                    continue;
                }

                BigDecimal x = getCellValueAsBigDecimal(row.getCell(24));
                BigDecimal y = getCellValueAsBigDecimal(row.getCell(25));

                ProjCoordinate wgs84Coord = CoordinateConverter.convertTMtoWGS84(x.doubleValue(), y.doubleValue());

                ShelterDTO shelter = ShelterDTO.builder()
                        .name(name)
                        .address(address)
                        .latitude(wgs84Coord.y)
                        .longitude(wgs84Coord.x)
                        .build();

                shelters.add(shelter);
            }

            workbook.close();
            return shelters;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the Excel file: " + e.getMessage());
        }
    }

    // 셀 값을 문자열로 변환하는 함수
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
            default:
                return "";
        }
    }

    // 셀 값을 BigDecimal로 변환하는 함수
    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING:
                try {
                    return new BigDecimal(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            default:
                return BigDecimal.ZERO;
        }
    }
}
