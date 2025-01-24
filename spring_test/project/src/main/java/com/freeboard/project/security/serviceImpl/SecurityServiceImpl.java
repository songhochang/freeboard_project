package com.freeboard.project.security.serviceImpl;

import com.freeboard.project.security.entity.Member;
import com.freeboard.project.security.entity.enums.MemberRole;
import com.freeboard.project.security.repository.MemberRepository;
import com.freeboard.project.security.dto.req.JoinReqDto;
import com.freeboard.project.security.dto.req.LoginReqDto;
import com.freeboard.project.security.dto.res.JoinResDto;
import com.freeboard.project.security.dto.res.LoginResDto;
import com.freeboard.project.security.entity.RefreshToken;
import com.freeboard.project.security.error.UserException;
import com.freeboard.project.security.repository.RefreshTokenRepository;
import com.freeboard.project.security.service.SecurityService;
import com.freeboard.project.security.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public JoinResDto join(JoinReqDto joinReqDto) {
        if (memberRepository.findByEmail(joinReqDto.getEmail()).isPresent()) {
            log.warn("회원가입 실패: 이미 가입된 이메일={}", joinReqDto.getEmail());
            return new JoinResDto(false, "이미 가입된 이메일입니다.");
        }

        Member newMember = new Member();
        newMember.setEmail(joinReqDto.getEmail());
        newMember.setName(joinReqDto.getName());
        newMember.setPassword(passwordEncoder.encode(joinReqDto.getPassword()));
        newMember.setMemberRole(MemberRole.USER);
        memberRepository.save(newMember);
        log.info("회원가입 성공: 이메일={}", joinReqDto.getEmail());
        return new JoinResDto(true, "회원가입에 성공하였습니다.");
    }

    @Transactional
    public LoginResDto login(LoginReqDto loginReqDto) {
        log.info("로그인 요청: 이메일={}", loginReqDto.getEmail());

        Member member = memberRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> new UserException("존재하지 않는 이메일입니다"));

        if (!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            log.warn("비밀번호 불일치: 이메일={}", loginReqDto.getEmail());
            throw new UserException("비밀번호가 일치하지 않습니다");
        }

        String jwtRefreshToken = jwtUtils.createJWTRefreshToken();
        RefreshToken refreshToken = new RefreshToken(
                jwtRefreshToken,
                member.getId(),
                jwtUtils.getTtlFromJwt(jwtRefreshToken)
        );
        refreshTokenRepository.save(refreshToken);

        String jwt = jwtUtils.createJWT(member.getEmail(), member.getPassword(), member.getMemberRole());
        log.info("Access Token과 Refresh Token 생성 완료: 이메일={}", member.getEmail());

        return new LoginResDto(jwt, jwtRefreshToken);
    }

}
