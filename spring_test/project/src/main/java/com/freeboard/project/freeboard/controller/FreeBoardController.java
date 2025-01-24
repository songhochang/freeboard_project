package com.freeboard.project.freeboard.controller;

import com.freeboard.project.freeboard.dto.req.CreateReqDto;
import com.freeboard.project.freeboard.dto.req.ModifyReqDto;
import com.freeboard.project.freeboard.dto.res.CreateResDto;
import com.freeboard.project.freeboard.dto.res.ModifyResDto;
import com.freeboard.project.freeboard.service.FreeBoardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/freeBoard")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<CreateResDto> createFreeBoard(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestBody CreateReqDto createReqDto){
        CreateResDto createResDto = freeBoardService.createFreeBoard(userDetails, createReqDto);
        return ResponseEntity.ok(createResDto);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/modify")
    public ResponseEntity<ModifyResDto> modifyFreeBoard(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestBody ModifyReqDto modifyReqDto){
        ModifyResDto modifyResDto = freeBoardService.modifyFreeBoard(userDetails, modifyReqDto);
        return ResponseEntity.ok(modifyResDto);
    }

}
