package com.freeboard.project.freeboard.service;

import com.freeboard.project.freeboard.dto.req.CreateReqDto;
import com.freeboard.project.freeboard.dto.req.ModifyReqDto;
import com.freeboard.project.freeboard.dto.res.CreateResDto;
import com.freeboard.project.freeboard.dto.res.ModifyResDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface FreeBoardService {
    CreateResDto createFreeBoard(UserDetails userDetails, CreateReqDto createReqDto);
    ModifyResDto modifyFreeBoard(UserDetails userDetails, ModifyReqDto modifyReqDto);
}
