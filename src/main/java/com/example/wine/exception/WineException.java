package com.example.wine.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WineException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "일시적으로 접속이 원활하지 않습니다.";
    private final HttpStatus status;

    public WineException() {
        super(DEFAULT_MESSAGE);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public WineException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public WineException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
