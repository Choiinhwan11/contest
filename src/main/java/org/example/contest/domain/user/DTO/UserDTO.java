package org.example.contest.domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private String userId ;
    private String userName ;
    private String userEmail;
    private String userPhone ;
    private String userNickName ;


}

