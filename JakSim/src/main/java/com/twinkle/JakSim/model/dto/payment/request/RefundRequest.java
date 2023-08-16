package com.twinkle.JakSim.model.dto.payment.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class RefundRequest {

//    @NotBlank
//    @Size(max = 10)
//    String cid; // 가맹점 코드, 10자

//    @Size(max=24)
//    String cid_secret; // 가맹점 코드 인증키 (24자, 숫자+영어 소문자 조합)

//    @NotBlank
    String tid; // 결제 고유 번호

//    @NotNull
    int cancel_amount; // 취소 금액

//    @NotNull
//    int cancel_tax_free_amount; // 취소 비과세 금액

//    int cancel_vat_amount; // 취소 부가세 금액 요청시 값을 전달하지 않을 경우,
//                            // (취소금액 - 취소 비과세 금액)/11, 소숫점이하 반올림.
//
//    int cancel_Available_amount; // 취소 가능 금액 (결제 취소 요청 금액 포함)

//    @Size(max=200)
//    String payload; // 해당 요청에 대해 저장하고 싶은 값 (최대 200자)
}
