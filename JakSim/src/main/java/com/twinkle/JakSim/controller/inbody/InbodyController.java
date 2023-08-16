package com.twinkle.JakSim.controller.inbody;

import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.LoginLogService;
import com.twinkle.JakSim.model.service.inbody.InbodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class InbodyController {
    private final LoginLogService loginLogService;
    private final AccountService accountService;
    private final InbodyService inbodyService;

    private final String defaultPath = "/content/mypage/";

    @GetMapping("/inbody")
    public String inbodyPage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("head_title", "인바디");
        model.addAttribute("access_log", loginLogService.findByUsernameRecent(user.getUsername()));
        model.addAttribute("user_info", accountService.findByUsername(user.getUsername()));
        model.addAttribute("totalPage", inbodyService.getTotalPage(user.getUsername()));

        return String.format(defaultPath + "inbody/body_info");
    }
}
