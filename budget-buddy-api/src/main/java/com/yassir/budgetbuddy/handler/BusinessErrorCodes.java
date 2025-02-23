package com.yassir.budgetbuddy.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@Getter
public enum BusinessErrorCodes {

    NO_CODE(0, NOT_IMPLEMENTED,"No code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST,"Current password is incorrect"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST,"The new password does not match"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN,"User account is disabled"),
    BAD_CREDENTIALS(304,FORBIDDEN,"login and / or password is incorrect "),
    EMAIL_ALREADY_EXISTS(305,BAD_REQUEST,"Email already exists"),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatusCode;

    BusinessErrorCodes(int code, HttpStatus httpStatusCode, String description) {
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }
}
