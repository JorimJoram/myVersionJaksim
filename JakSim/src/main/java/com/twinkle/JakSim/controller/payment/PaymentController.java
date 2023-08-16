package com.twinkle.JakSim.controller.payment;

import com.twinkle.JakSim.model.dto.payment.PaymentDtoForMypage;
import com.twinkle.JakSim.model.dto.payment.response.CancelResponse;
import com.twinkle.JakSim.model.dto.payment.response.ListResponse;
import com.twinkle.JakSim.model.dto.payment.response.PaymentDo;
import com.twinkle.JakSim.model.dto.trainer.ProductDto;
import com.twinkle.JakSim.model.service.payment.PaymentService;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import com.twinkle.JakSim.model.dto.payment.response.ApproveResponse;
import com.twinkle.JakSim.model.service.payment.KakaoPayService;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final PaymentService paymentService;
    private final TrainerService trainerService;


    private final String defaultPage = "/content/payment/";

    // 결제 진행 중 취소
    @GetMapping("/cancel")
    public String cancel() {
        return defaultPage + "kakaoPay/Cancle";
    }

    // 결제 승인 (성공)
    @GetMapping("/success")
    public String afterPayRequest(@AuthenticationPrincipal User user, @RequestParam("pg_token") String pgToken, Model model) {
        ApproveResponse kakaoApprove = kakaoPayService.approveResponse(user.getUsername(), pgToken);

        if(kakaoApprove != null) {
            if(paymentService.savePaymentDetails(user.getUsername(), kakaoApprove)) {
                kakaoApprove.setApproved_at(kakaoApprove.getApproved_at().replace("T", " "));
                model.addAttribute("kakaoApprove", kakaoApprove);

                return defaultPage + "kakaoPay/Success";
            }
        }

        // 일단은 에러처리 안함.
        return defaultPage + "kakaoPay/Success";
    }

    @GetMapping("/refundSuccess/{tid}")
    public String approveRefund(@PathVariable("tid") String tid, Model model) {
       paymentService.refund(tid).ifPresent(
                item-> {
                    model.addAttribute("refund", item);
                }
        );

        return defaultPage + "kakaoPay/Refund";
    }

    @GetMapping("/list")
    public String payListPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("head_title", "결제 확인 목록");
        model.addAttribute("total_page", paymentService.getTotalPage(user.getUsername()));

        return String.format(defaultPage + "pay_list");
    }

    // 확인 필요
    @GetMapping("/detail/{tid}")
    public String payDetailPage(@AuthenticationPrincipal User user, @PathVariable("tid") String tid, Model model) {
        model.addAttribute("head_title", "결제 내역 상세");
        paymentService.getPaymentByTid(tid).ifPresent(
                item -> {
                    model.addAttribute("apiResponse", kakaoPayService.kakaoList(tid));
                    model.addAttribute("payment", item);
                    ProductDto product = paymentService.getProductByIdx(item.getTp_idx());
                    model.addAttribute("product", product);
                    model.addAttribute("trainer", trainerService.searchByUsername(product.getTrainerId())); // 수정함
                }
        );

        return String.format(defaultPage + "pay_detail");
    }

    @GetMapping("/api/{page}")
    @ResponseBody
    public List<PaymentDo> payListItem(@AuthenticationPrincipal User user, @PathVariable("page") int page){
        return paymentService.getPageItem(user.getUsername(), page);
    }
    
}
