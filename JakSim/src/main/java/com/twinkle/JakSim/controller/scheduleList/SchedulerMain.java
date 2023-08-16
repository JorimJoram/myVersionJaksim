package com.twinkle.JakSim.controller.scheduleList;

import com.twinkle.JakSim.model.service.payment.PaymentService;
import com.twinkle.JakSim.model.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scheduler")
@RequiredArgsConstructor
public class SchedulerMain {

    private final PaymentService paymentService;
    private final TrainerService trainerService;

    @GetMapping("")
    public String scheduler(@AuthenticationPrincipal User user, Model model) {
        if(user == null){
            return "redirect:/login";
        }
        String isUser = "USER";
        if(user != null) {
            model.addAttribute("name", trainerService.searchTrainerName(user.getUsername()));
        }

        for(GrantedAuthority authority : user.getAuthorities()){
            String authorityString = authority.getAuthority();

            if(authorityString.contains(isUser)) {
                if(paymentService.findValidPtList(user.getUsername()).isEmpty()) {
                    return "content/scheduleList/warnScheduleList";
                }

                return "content/scheduleList/generalScheduleList";
            }
        }

        return "content/scheduleList/trainerScheduleList";
    }
}
