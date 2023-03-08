package com.example.wine.jwt;

import com.example.wine.dto.login.LoginRequest;
import com.example.wine.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Builder
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;


    public static JwtAuthenticationFilter of(JwtTokenProvider jwtTokenProvider) {
        return JwtAuthenticationFilter.builder()
                .jwtTokenProvider(jwtTokenProvider)
                .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        //filter에서 header를 가져옴
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if(!tokenService.checkBlacklist(authorizationHeader)){
            //token 값에서 유효값 (email, role)을 추출하여 userDTO를 만듦
            LoginRequest loginRequest = jwtTokenProvider.getUserDtoOf(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    loginRequest,
                    "",
                    loginRequest.getAuthorities()));
            }

        } catch (ExpiredJwtException exception) {
            logger.error("ExpiredJwtException : expired token");
        } catch (Exception exception) {
            logger.error("Exception : no token");
        }
        filterChain.doFilter(request, response);
    }
}
