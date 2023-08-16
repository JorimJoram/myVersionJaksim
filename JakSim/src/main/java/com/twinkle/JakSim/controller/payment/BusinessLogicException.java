package com.twinkle.JakSim.controller.payment;

import com.twinkle.JakSim.model.dto.Enum.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}