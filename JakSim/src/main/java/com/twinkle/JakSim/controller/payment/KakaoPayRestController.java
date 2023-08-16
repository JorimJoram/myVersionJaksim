package com.twinkle.JakSim.controller.payment;

import com.twinkle.JakSim.model.dto.Enum.ErrorCode;
import com.twinkle.JakSim.model.dto.payment.request.PaymentRequest;
import com.twinkle.JakSim.model.dto.payment.request.RefundRequest;
import com.twinkle.JakSim.model.dto.payment.response.CancelResponse;
import com.twinkle.JakSim.model.dto.payment.response.ReadyResponse;
import com.twinkle.JakSim.model.service.payment.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayRestController {

    private final KakaoPayService kakaoPayService;

    // 결제 요청 (준비)
    @PostMapping("/ready")
    public ReadyResponse readyToKakaoPay(@AuthenticationPrincipal User user,
                                         @Valid @RequestBody PaymentRequest paymentRequest) {
        return kakaoPayService.kakaoPayReady(user.getUsername(), paymentRequest);
    }

    // 결제 실패
    @GetMapping("/fail")
    public void fail() {
        throw new BusinessLogicException(ErrorCode.PAY_FAILED);
    }

    // 환불
    @PostMapping("/refund")
    public ResponseEntity refund(@Valid @RequestBody RefundRequest refundRequest) {
        CancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(refundRequest);

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }

    // 조회

}