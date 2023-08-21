package com.twinkle.JakSim.controller.administrator;

import com.twinkle.JakSim.model.dto.account.LoginLogStat;
import com.twinkle.JakSim.model.dto.account.UserStat;
import com.twinkle.JakSim.model.service.account.AccountService;
import com.twinkle.JakSim.model.service.account.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/man/api")
public class AdminRestApi {
    private final LoginLogService loginLogService;
    private final AccountService accountService;
    @GetMapping("/visit/all")
    public int getAllAccess(){
        return loginLogService.getAllAccess();
    }

    @GetMapping("/visit/date")
    public List<LoginLogStat> getAmountDateAllUsers(@RequestParam(required = false) String start, @RequestParam(required = false) String end){
        return loginLogService.getAmountDate(start, end);
    }

    @GetMapping("/visit/user")
    public List<LoginLogStat> getAmountDateSingleUsers(@RequestParam String username, @RequestParam(required = false)String start, @RequestParam(required = false)String end){
        return loginLogService.getAmountSingleUser(username, start, end);
    }

    @GetMapping("/visit/group-user")
    public List<LoginLogStat> getAmountDateGroupUsers(@RequestParam(defaultValue = "", required = false) String start,
                                                      @RequestParam(defaultValue = "", required = false) String end, @RequestParam(required = false) boolean order){
        return loginLogService.getAmountGroupUser(start, end, order);
    }

    @GetMapping("/account/amount")
    public List<UserStat> getAmountAccounts(@RequestParam(defaultValue = "", required = false) String start,
                                            @RequestParam(defaultValue = "", required = false) String end){
        return accountService.getAmountAccounts(start, end);
    }

    @GetMapping("/account/ratio")
    public List<UserStat> getAmountByRole(){
        return accountService.getAmountByRole();
    }
}
