package com.freeboard.project.security.dto.res;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinResDto {

    private boolean success;

    private String message;

}
