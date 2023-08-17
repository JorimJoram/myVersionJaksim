package com.twinkle.JakSim.controller.chat;

import com.twinkle.JakSim.model.dto.chat.ChatRoom;
import com.twinkle.JakSim.model.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/create")
    public ChatRoom createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }

    @GetMapping("/find")
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
