package com.example.wine.controller;

import com.example.wine.dto.login.LoginRequest;
import com.example.wine.dto.login.LoginResponse;
import com.example.wine.dto.login.SignupRequest;
import com.example.wine.service.LoginService;
import com.example.wine.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"회원 서비스"}, description = "회원가입, 로그인, 로그아웃")
public class LoginController {

    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "회원 등록 성공 시 DB에 저장한다.")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest) {
        loginService.signup(signupRequest);
        return ResponseEntity
                .created(URI.create("/signup"))
                .build();
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "이메일과 패스워드를 입력받아 로그인 시도, 성공시 토큰 발급한다.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(loginService.login(loginRequest));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그인 토큰을 블랙리스트 테이블에 저장한다.")
    public ResponseEntity<Void> logOut(@RequestHeader(name = "Authorization") String token) {
        System.out.println(token);
        tokenService.logout(token);
        return ResponseEntity.ok().build();
    }
}
