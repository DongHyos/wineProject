package com.example.wine.service;

import com.example.wine.dto.login.LoginRequest;
import com.example.wine.dto.login.LoginResponse;
import com.example.wine.dto.login.SignupRequest;
import com.example.wine.entity.Member;
import com.example.wine.exception.member.DuplicateEmailException;
import com.example.wine.exception.member.DuplicateNicknameException;
import com.example.wine.exception.member.IdPasswordMismatchException;
import com.example.wine.exception.member.NoSuchMemberException;
import com.example.wine.jwt.JwtTokenProvider;
import com.example.wine.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(SignupRequest signupRequest) {
        duplicateEmail(signupRequest.getEmail());
        duplicateNickname(signupRequest.getNickname());
        signupRequest.setUserPw(encodingPassword(signupRequest.getUserPw()));
        loginRepository.save(signupRequest.toEntity());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member findMember = loginRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NoSuchMemberException::new);

        passwordMustBeSame(loginRequest.getUserPw(), findMember.getUserPw());
        String token = jwtTokenProvider.makeJwtToken(loginRequest);

        return LoginResponse.from(findMember, token);
    }

    private void passwordMustBeSame(String reqPw, String pw) {
        if (!passwordEncoder.matches(reqPw, pw)) {
            throw new IdPasswordMismatchException();
        }
    }

    private String encodingPassword(String pw) {
        return passwordEncoder.encode(pw);
    }

    public void duplicateNickname(final String nickname) {
        if (loginRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }

    public void duplicateEmail(final String email) {
        if (loginRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
