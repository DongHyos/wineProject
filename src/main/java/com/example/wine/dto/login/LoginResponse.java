package com.example.wine.dto.login;

import com.example.wine.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {

    private String email;
    private String username;
    private String nickname;
    private String token;

    public static LoginResponse from(final Member member, final String token) {
        return new LoginResponse(
                member.getEmail(),
                member.getUsername(),
                member.getNickname(),
                token
        );
    }
}
