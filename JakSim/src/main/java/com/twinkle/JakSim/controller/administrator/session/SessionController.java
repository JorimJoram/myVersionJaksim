package com.twinkle.JakSim.controller.administrator.session;

import com.twinkle.JakSim.model.dto.administrator.session.SessionInfo;
import com.twinkle.JakSim.model.dto.administrator.session.UserSession;
import com.twinkle.JakSim.model.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SessionController {
    private final SessionRegistry registry;
    private final AccountService accountService;
    private final String defaultPath = "content/administrator/session/";

    @GetMapping("/session/list")
    public String sessions(Model model) {
        List<UserSession> userSessions = registry.getAllPrincipals().stream()
                .map(p -> UserSession.builder()
                        .username(((User) p).getUsername())
                        .role(checkRole(accountService.findByUsername(((User) p).getUsername()).getRole()))
                        .sessions(registry.getAllSessions(p, false)
                                .stream().map(si -> SessionInfo.builder()
                                        .lastRequest(dateToLocalDateTime(si.getLastRequest()))
                                        .sessionId(si.getSessionId())
                                        .build()
                                ).collect(Collectors.toList()))
                        .build()).sorted(new Comparator<UserSession>() {
                    @Override
                    public int compare(UserSession o1, UserSession o2) {
                        int result = 0;
                        if(o1 != null && !o2.getSessions().isEmpty())
                            result = o2.getSessions().get(0).getLastRequest().compareTo(o1.getSessions().get(0).getLastRequest());
                        return result;
                    }
                }).collect(Collectors.toList());

        model.addAttribute("sessionList", userSessions);
        model.addAttribute("amount_user", userSessions.size());

        return defaultPath + "sessionList";
    }

    private String checkRole(int role) {
        switch (role){
            case 0:
                return "관리자";
            case 1:
                return "일반 사용자";
            case 2:
                return "트레이너";
            default:
                return "";
        }
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @PostMapping("/session/expire")
    public String expireSession(@RequestBody Map<String, String> sessionId) {
        SessionInformation session = registry.getSessionInformation(sessionId.get("sessionId"));
        if(!session.isExpired()){
            session.expireNow();
        }
        return "redirect:/session/list";
    }

    @GetMapping("/session/expired")
    public String sessionExpired() {
        return defaultPath + "sessionExpired";
    }
}
