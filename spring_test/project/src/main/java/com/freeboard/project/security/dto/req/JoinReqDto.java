package com.freeboard.project.security.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JoinReqDto {

    private String name;

    private String email;

    private String password;

}
