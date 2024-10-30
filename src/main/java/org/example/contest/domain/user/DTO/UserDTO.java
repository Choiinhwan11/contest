package org.example.contest.domain.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONUtil;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String userId ;
    private String userName ;
    private String userEmail;
    private String userPhone ;
    private String userNickName ;



}

