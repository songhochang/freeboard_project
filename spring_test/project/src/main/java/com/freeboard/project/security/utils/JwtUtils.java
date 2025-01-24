package com.freeboard.project.security.utils;

import com.freeboard.project.entity.enums.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    private static final String SECRET_KEY = "coleparmer20nicolasjackson15jadensancho19";
    private static final SecretKey SECRET_KEY_SPEC = new SecretKeySpec(
            Base64.getDecoder().decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes())),
            "HmacSHA256"
    );
    private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7;

    public String createJWT(String email, String password, MemberRole role){
        return Jwts.builder()
                .claim("email", email)
                .claim("password", password)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SECRET_KEY_SPEC)
                .compact();
    }

    public String createJWTRefreshToken(){
        long now = System.currentTimeMillis();
        long expirationTime = now + REFRESH_TOKEN_VALIDITY; // TTL은 밀리초로 계산
        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime))
                .signWith(SECRET_KEY_SPEC, SignatureAlgorithm.HS256)
                .compact();
    }

    public long getTtlFromJwt(String jwt) {
        Claims claims = decodeJwt(jwt);
        return (claims.getExpiration().getTime() - claims.getIssuedAt().getTime()) / 1000;
    }

    public Claims decodeJwt(String jwt){

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY_SPEC)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String getEmailFromJwt(String jwt){
        Claims claims = decodeJwt(jwt);
        return claims.get("email").toString();
    }

    public String getPasswordFromJwt(String jwt){
        Claims claims = decodeJwt(jwt);
        return claims.get("password").toString();
    }

    public String getRoleFromJwt(String jwt){
        Claims claims = decodeJwt(jwt);
        return claims.get("role").toString();
    }

    public boolean isValidToken(String jwt){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY_SPEC)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidJwtToken(String jwt){
        return isValidToken(jwt);
    }

    public boolean isValidRefreshToken(String refreshToken){
        return isValidToken(refreshToken);
    }



}
