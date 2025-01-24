package com.freeboard.project.security.controller;

import com.freeboard.project.security.dto.req.JoinReqDto;
import com.freeboard.project.security.dto.req.LoginReqDto;
import com.freeboard.project.security.dto.res.JoinResDto;
import com.freeboard.project.security.dto.res.LoginResDto;
import com.freeboard.project.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
@CrossOrigin
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/join")
    public ResponseEntity<JoinResDto> join(@RequestBody JoinReqDto joinReqDto){
        JoinResDto joinResDto = securityService.join(joinReqDto);
        return ResponseEntity.ok(joinResDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginReqDto){
        LoginResDto loginResDto = securityService.login(loginReqDto);
        return ResponseEntity.ok(loginResDto);
    }

}
