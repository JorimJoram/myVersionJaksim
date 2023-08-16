package com.twinkle.JakSim.controller.administrator;

import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.FileService;
import com.twinkle.JakSim.model.service.account.LoginLogService;
import com.twinkle.JakSim.model.service.payment.PaymentService;
import com.twinkle.JakSim.model.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/man")
public class AdminController {
    private final AccountService accountService;
    private final FileService fileService;
    private final ReviewService reviewService;
    private final PaymentService paymentService;
    private final LoginLogService loginLogService;

    @GetMapping("/main")
    public String manageMainPage(Model model){

        return "content/administrator/main";
    }

    @GetMapping("/info/{username}")
    public String infoPage(@PathVariable("username") String username, Model model){
        final String infoPath = "content/administrator/info/";
        model.addAttribute("user", accountService.findByUsername(username));
        model.addAttribute("imageList", fileService.getAllImages(username));
        //model.addAttribute("reviewList", reviewService.showMyReview(username));
        model.addAttribute("paymentList", paymentService.getPageItem(username, 1));

        return String.format(infoPath+"info");
    }


}
