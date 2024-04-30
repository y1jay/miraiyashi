package com.miraiyashi.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, 401, "사용자를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "실행 오류."),
    NOT_FOUND_DATA(HttpStatus.NOT_FOUND, 404, "데이터를 찾을 수 없습니다."),
    NOT_HAVE_COUNT(HttpStatus.NOT_FOUND, 402, "보유한 횟수가 부족합니다.");

    private final HttpStatus httpStatus;	// HttpStatus
    private final int code;				//
    private final String message;			// 설명
    }