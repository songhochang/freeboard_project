package com.freeboard.project.security.dto.res;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {

    private String jwt;

    private String refreshToken;

}
