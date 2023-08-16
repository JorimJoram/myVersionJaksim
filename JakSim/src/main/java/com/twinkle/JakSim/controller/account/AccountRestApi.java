package com.twinkle.JakSim.controller.account;

import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account/api")
@RequiredArgsConstructor
public class AccountRestApi {
    private final AccountService accountService;
    private final FileService fileService;

    @PostMapping("/register")
    public int accountAction(@RequestBody UserDto data){
        return accountService.CreateMember(data);
    }

    @GetMapping("/verify-id")
    public boolean checkId(@RequestParam String userId){
        return accountService.findByUsername(userId) == null;
    }

    @GetMapping("/verify-tel")
    public boolean checkTel(@RequestParam String tel){
        return accountService.findByTel(tel) == null;
    }

    @PutMapping("/change-pw")
    public int updatePassword(@RequestBody UserDto data){
        return accountService.update(data.getId(), data.getPw());
    }

    @PutMapping("/change-tel")
    public int updateTel(@AuthenticationPrincipal User user, @RequestBody UserDto data){
        return accountService.updateTel(data.getTel(), user.getUsername());
    }

    @PutMapping("/change-name")
    public int updateName(@AuthenticationPrincipal User user, @RequestBody UserDto data){
        return accountService.updateName(data.getName(), user.getUsername());
    }

    @PutMapping("/change-image")
    public boolean updateProfileImage(@AuthenticationPrincipal User user, @RequestParam("file")MultipartFile data) throws Exception{
        return fileService.updateProfileImage(data, user.getUsername());
    }

    @GetMapping("/user-info")
    public UserDto getUserInfoByUserId(@RequestParam String username){
        return accountService.findByUsername(username);
    }

    @DeleteMapping("/delete")
    public int deleteUser(@AuthenticationPrincipal User user){
        return accountService.delete(user.getUsername());
    }
}
