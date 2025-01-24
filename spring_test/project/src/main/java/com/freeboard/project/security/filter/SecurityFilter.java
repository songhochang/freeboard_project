package com.freeboard.project.security.filter;

import com.freeboard.project.security.entity.Member;
import com.freeboard.project.security.repository.MemberRepository;
import com.freeboard.project.security.entity.RefreshToken;
import com.freeboard.project.security.error.UserException;
import com.freeboard.project.security.repository.RefreshTokenRepository;
import com.freeboard.project.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    public SecurityFilter(JwtUtils jwtUtils,MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository){
        this.memberRepository = memberRepository;
        this.jwtUtils = jwtUtils;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String jwt = authorization.split("Bearer ")[1];
                if (jwtUtils.isValidJwtToken(jwt)) {
                    authenticateUser(jwt);
                } else {
                    handleRefreshToken(request, response, jwt);
                }
            }
        } catch (UserException ex) {
            log.warn("Authentication failed: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + ex.getMessage());
            return;
        } catch (Exception ex) {
            log.error("Unexpected error in SecurityFilter: {}", ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error: " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String jwt) {
        String email = jwtUtils.getEmailFromJwt(jwt);
        String password = jwtUtils.getPasswordFromJwt(jwt);
        String role = jwtUtils.getRoleFromJwt(jwt);

        if (email == null) {
            throw new UserException("사용자의 이메일을 찾을 수 없습니다");
        }

        Set<SimpleGrantedAuthority> roles = Set.of(new SimpleGrantedAuthority("ROLE_" + role));
        UserDetails userDetails = User.builder()
                .username(email)
                .password(password)
                .authorities(roles)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated user: {}", email);
    }

    private void handleRefreshToken(HttpServletRequest request, HttpServletResponse response, String jwt)
            throws IOException {
        String email = jwtUtils.getEmailFromJwt(jwt);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("사용자를 찾을 수 없습니다"));

        RefreshToken token = refreshTokenRepository.findByMemberId(member.getId());
        if (token == null || token.getJwtRefreshToken() == null || !jwtUtils.isValidRefreshToken(token.getJwtRefreshToken())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT 토큰 및 REFRESH 토큰이 유효하지 않습니다");
            log.warn("Refresh token invalid for user: {}", email);
            return;
        }

        String newJwt = jwtUtils.createJWT(email, member.getPassword(), member.getMemberRole());

        response.setHeader("Authorization", "Bearer " + newJwt);
        log.info("New JWT issued for user: {}", email);
    }

}
