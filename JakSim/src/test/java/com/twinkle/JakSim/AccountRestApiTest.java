package com.twinkle.JakSim;

import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.service.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountRestApiTest {
    @Autowired
    private AccountService accountService;

    @Test
    void Create(){
        UserDto userDto = new UserDto();
        userDto.setId("tester2");
        userDto.setPw("1234");
        userDto.setBirth("1989-03-30");
        //userDto.setTel("0102");
        userDto.setName("정혜화");
        userDto.setEmail("wkdgyfla97@tester.com");
        userDto.setGender(1);
        userDto.setRole(1);
        System.out.println(accountService.CreateMember(userDto));
    }

    @Test
    void ReadByIdSuccess(){
        System.out.println(accountService.findByUsername("test"));
    }

    @Test
    void ReadByIdFail(){
        System.out.println(accountService.findByUsername("a"));
    }

    @Test
    void ReadByTelSuccess(){
        System.out.println(accountService.findByTel("01012341234"));
    }

    @Test
    void ReadByTelFail(){
        System.out.println(accountService.findByTel("1"));
    }

    @Test
    void checkPassword(){
        System.out.println(accountService.checkPassword("test", "12341234"));
    }
}
