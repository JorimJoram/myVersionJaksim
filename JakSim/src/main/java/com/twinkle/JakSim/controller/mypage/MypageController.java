package com.twinkle.JakSim.controller.mypage;

import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.LoginLogService;
import com.twinkle.JakSim.model.service.inbody.InbodyService;
import com.twinkle.JakSim.model.service.payment.PaymentService;
import com.twinkle.JakSim.model.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final String defaultPath = "/content/mypage/";
    private final AccountService accountService;
    private final LoginLogService loginLogService;
    private final PaymentService paymentService;
    private final ReviewService reviewService;

    @GetMapping("/auth")
    public String authPage(Model model){
        model.addAttribute("head_title", "개인페이지");
        return String.format(defaultPath + "auth");
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("head_title", "개인페이지");
        model.addAttribute("user_info", accountService.findByUsername(user.getUsername()));
        model.addAttribute("log", loginLogService.findByUsernameRecent(user.getUsername()));
        paymentService.getRecentPaymentBy3(user.getUsername()).ifPresent(item -> {
            if(!item.isEmpty()){
                model.addAttribute("payment", item);
            }
        });
        reviewService.showMyReviewForMyPage(user.getUsername()).ifPresent(ReviewDtos -> {if (!ReviewDtos.isEmpty()) {
            model.addAttribute("reviewList", ReviewDtos);
        }});

        return String.format(defaultPath + "mypage");
    }

    @GetMapping("/history/login")
    public String logPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("head_title", user.getUsername() + "님 이력확인");
        model.addAttribute("user_info", accountService.findByUsername(user.getUsername()));

        model.addAttribute("access_log", loginLogService.findByUsername(user.getUsername(), 2));
        return String.format(defaultPath + "log");
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user_info", accountService.findByUsername(user.getUsername()));
        model.addAttribute("log", loginLogService.findByUsernameRecent(user.getUsername()));
        return String.format(defaultPath + "profile");
    }
}
