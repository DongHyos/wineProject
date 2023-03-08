package com.example.wine.exception.member;

import com.example.wine.exception.InputFieldException;
import org.springframework.http.HttpStatus;

public class DuplicateNicknameException extends InputFieldException {

    private static final String MESSAGE = "이미 사용 중인 닉네임입니다.";

    public DuplicateNicknameException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST, NICKNAME);
    }
}
