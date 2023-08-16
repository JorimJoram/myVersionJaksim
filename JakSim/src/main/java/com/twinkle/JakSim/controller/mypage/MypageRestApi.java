package com.twinkle.JakSim.controller.mypage;

import com.twinkle.JakSim.model.dto.account.LoginLogDto;
import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.dto.inbody.InbodyDto;
import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.FileService;
import com.twinkle.JakSim.model.service.account.LoginLogService;
import com.twinkle.JakSim.model.service.inbody.InbodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/mypage/api")
@RequiredArgsConstructor
public class MypageRestApi {
    private final AccountService accountService;
    private final LoginLogService loginLogService;
    @PostMapping("/auth")
    public String authPassword(@RequestBody UserDto userDto, @AuthenticationPrincipal User user){
        return accountService.checkPassword(user.getUsername(), userDto.getPw()).get() ? user.getUsername() : null;
    }

    @GetMapping("/session-time")
    public int getSessionTime(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            long currentTimeMills = System.currentTimeMillis();
            long lastAccessedTimeMills = session.getLastAccessedTime();
            int maxInactiveInterval = session.getMaxInactiveInterval();
            long elapsedTimeInSeconds = (currentTimeMills - lastAccessedTimeMills) / 1000;
            return maxInactiveInterval - (int) elapsedTimeInSeconds;
        }
        return 0;
    }


    @GetMapping("login/{page}")
    public List<LoginLogDto> getLoginList(@PathVariable("userId") String username, @PathVariable("page")int pageNum){
        return loginLogService.findByUsername(username, pageNum);
    }
}
