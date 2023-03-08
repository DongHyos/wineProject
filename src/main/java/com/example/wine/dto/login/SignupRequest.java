package com.example.wine.dto.login;

import com.example.wine.entity.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.wine.dto.ValidatorMessage.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequest {

    @NotBlank(message = EMPTY_MESSAGE)
    @Email(message = EMAIL_MESSAGE)
    private String email;
    @NotBlank(message = EMPTY_MESSAGE)
    @Pattern(regexp = MEMBER_PW_FORMAT, message = MEMBER_PW_MESSAGE)
    private String userPw;
    @NotBlank(message = EMPTY_MESSAGE)
    private String username;
    @NotBlank(message = EMPTY_MESSAGE)
    private String nickname;
    @NotBlank(message = EMPTY_MESSAGE)
    private String postcode;
    @NotBlank(message = EMPTY_MESSAGE)
    private String address;
    @NotBlank(message = EMPTY_MESSAGE)
    private String address2;
    @NotBlank(message = EMPTY_MESSAGE)
    private String phone;
    @NotBlank(message = AGREE_MESSAGE)
    private String agreement;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .userPw(this.userPw)
                .username(this.username)
                .nickname(this.nickname)
                .postcode(this.postcode)
                .address(this.address)
                .address2(this.address2)
                .phone(this.phone)
                .agreement(this.agreement)
                .build();
    }

}
