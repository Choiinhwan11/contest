package org.example.contest.domain.defense.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ShelterDTO {
    private String name;
    private String address;
    private double latitude;
    private double longitude;


}
