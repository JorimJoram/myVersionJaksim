package com.twinkle.JakSim.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatTestController {
    @GetMapping("/chat/test")
    public String testPage(){
        return "content/qna/test";
    }
}
