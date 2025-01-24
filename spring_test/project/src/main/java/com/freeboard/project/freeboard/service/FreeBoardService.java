package com.freeboard.project.freeboard.service;

import com.freeboard.project.freeboard.dto.req.CreateReqDto;
import com.freeboard.project.freeboard.dto.res.CreateResDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface FreeBoardService {
    CreateResDto createFreeBoard(UserDetails userDetails, CreateReqDto createReqDto);
}
