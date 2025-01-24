package com.freeboard.project.security.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginReqDto {

    private String email;

    private String password;

}
