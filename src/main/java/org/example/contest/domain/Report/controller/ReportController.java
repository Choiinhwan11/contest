package org.example.contest.domain.Report.controller;


import lombok.RequiredArgsConstructor;
import org.example.contest.domain.Report.DTO.ReportRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/Report")
public class ReportController {


    @PostMapping("/Message")
    public ResponseEntity<String> receiveReport(@RequestBody ReportRequest request) {

        System.out.println("긴급 신고 접수:");
        System.out.println("위도: " + request.getLat());
        System.out.println("경도: " + request.getLng());
        System.out.println("메시지: " + request.getMessage());

        return new ResponseEntity<>("신고가 성공적으로 접수되었습니다.", HttpStatus.OK);
    }
}
