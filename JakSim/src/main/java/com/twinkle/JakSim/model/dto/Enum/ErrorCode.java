package com.twinkle.JakSim.model.dto.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    PAY_FAILED(HttpStatus.BAD_REQUEST, "결제 실패");
//    PAY_CANCEL(HttpStatus.BAD_REQUEST, "진행 중인 결제 취소");

    private final HttpStatus status;
    private final String message;

    }