package com.twinkle.JakSim.controller.account;

import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email/api")
public class EmailRestApi {
    private final AccountService accountService;

    @PostMapping("/send")
    public String sendMail(@RequestBody UserDto data){
        return accountService.validateEmail(data.getEmail());
    }

    @PutMapping("/modify")
    public int changeEmail(@AuthenticationPrincipal User user, @RequestBody UserDto userDto){
        return accountService.updateEmail(userDto.getEmail(), user.getUsername());
    }

    @GetMapping("/verify-email")
    public boolean emailDuplicateVerify(@RequestParam String email){
        return accountService.findByEmail(email).isEmpty();
    }

    @GetMapping("/user-info")
    public UserDto getUserInfoByEmail(@RequestParam String email){
        return accountService.findByEmail(email).orElseThrow();
    }
}
