package org.example.contest.domain.profile.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileInfoDTO {
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userNickName;
}
