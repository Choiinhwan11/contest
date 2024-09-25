package org.example.contest.domain.defense.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.contest.domain.defense.dto.ShelterDTO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefenseServiceImpl implements DefenseService {

    private final String EXCEL_FILE_URL = "https://www.localdata.go.kr/datafile/etc/LOCALDATA_ALL_12_04_12_E.xlsx";

    public List<ShelterDTO> fetchAllShelters() throws Exception {
        // 파일 URL에서 엑셀 파일을 읽기
        InputStream inputStream = new URL(EXCEL_FILE_URL).openStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        List<ShelterDTO> shelters = new ArrayList<>();

        // 엑셀 데이터 행별로 처리
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;  // 첫 번째 행(헤더)는 건너뛰기
            }

            // 엑셀 셀 값이 null일 때 처리
            String name = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "";
            String address = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "";
            Double latitude = row.getCell(2) != null ? row.getCell(2).getNumericCellValue() : null;
            Double longitude = row.getCell(3) != null ? row.getCell(3).getNumericCellValue() : null;

            ShelterDTO shelter = ShelterDTO.builder()
                    .name(name)
                    .address(address)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();

            shelters.add(shelter);
        }

        // 리소스 닫기
        workbook.close();
        inputStream.close();

        return shelters;
    }
}
