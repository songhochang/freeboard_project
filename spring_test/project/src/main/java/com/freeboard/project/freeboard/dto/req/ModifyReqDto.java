package com.freeboard.project.freeboard.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReqDto {

    private Long freeBoardId;
    private String title;
    private String content;

}
