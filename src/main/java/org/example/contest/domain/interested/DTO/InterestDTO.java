package org.example.contest.domain.interested.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestDTO {
    private String province;  // 시/도
    private String city;      // 시/군/구
    private String district;  // 읍/면/동
    private String relation;  // 사용자와 관련성
    private String comment;   // 사용자가 남긴 코멘트
    private String userId;
    private Long id;

}
