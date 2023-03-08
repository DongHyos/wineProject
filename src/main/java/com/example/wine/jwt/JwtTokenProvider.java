package com.example.wine.jwt;

import com.example.wine.dto.login.LoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {// 토큰 생성, 검증 하는 객체

    //토큰 생성에 필요한 변수값
    private final JwtProperties jwtProperties;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String makeJwtToken(LoginRequest user) {//토큰 생성
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public LoginRequest getUserDtoOf(String token) {
        Claims claims= null;
        if(token!=null) {
            try{
                if(validationAuthorizationHeader(token)){
                    token = extractToken(token);
                    claims = parsingToken(token);
                    return new LoginRequest(claims);
                }
            }catch (Exception e){
                return null;
            }
        }else{
            return null;
        }
        return null;
    }

    private Claims parsingToken(String token) { //Token 값을 claims로 바꿔주는 메서드
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean validationAuthorizationHeader(String header) { //헤더값이 유효한지 검증하는 메서드

        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            return false;
        }
        return true;
    }

    private String extractToken(String authorizationHeader) { //토큰 (Bearer) 떼고 토큰값만 가져오는 메서드
        return authorizationHeader.substring(jwtProperties.getTokenPrefix().length());
    }

}
