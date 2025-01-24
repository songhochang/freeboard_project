package com.freeboard.project.freeboard.serviceImpl;

import com.freeboard.project.freeboard.dto.req.CreateReqDto;
import com.freeboard.project.freeboard.dto.res.CreateResDto;
import com.freeboard.project.freeboard.entity.FreeBoard;
import com.freeboard.project.freeboard.repository.FreeBoardRepository;
import com.freeboard.project.freeboard.service.FreeBoardService;
import com.freeboard.project.security.entity.Member;
import com.freeboard.project.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;

    @Transactional
    public CreateResDto createFreeBoard(UserDetails userDetails, CreateReqDto createReqDto){
        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        FreeBoard freeBoard = new FreeBoard();
        freeBoard.setContent(createReqDto.getContent());
        freeBoard.setTitle(createReqDto.getTitle());
        freeBoard.setMember(member);
        freeBoard.setCreatedDate(LocalDateTime.now());

        freeBoardRepository.save(freeBoard);

        return new CreateResDto(freeBoard.getId(), freeBoard.getTitle(), freeBoard.getContent(), freeBoard.getCreatedDate(), freeBoard.getMember().getId(), freeBoard.getMember().getName());
    }

}
