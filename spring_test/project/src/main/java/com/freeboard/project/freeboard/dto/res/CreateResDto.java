package com.freeboard.project.freeboard.dto.res;

import com.freeboard.project.security.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResDto {

    private Long freeBoardId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Member member;

}
