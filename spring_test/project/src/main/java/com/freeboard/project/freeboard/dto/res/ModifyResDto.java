package com.freeboard.project.freeboard.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyResDto {

    private Long freeBoardId;
    private String title;
    private String content;
    private LocalDateTime modifiedDate;
    private Long member_id;
    private String memberName;

}
