package com.freeboard.project.security.service;

import com.freeboard.project.security.dto.req.JoinReqDto;
import com.freeboard.project.security.dto.req.LoginReqDto;
import com.freeboard.project.security.dto.res.JoinResDto;
import com.freeboard.project.security.dto.res.LoginResDto;

public interface SecurityService {
    JoinResDto join(JoinReqDto joinReqDto);
    LoginResDto login(LoginReqDto loginReqDto);
}
