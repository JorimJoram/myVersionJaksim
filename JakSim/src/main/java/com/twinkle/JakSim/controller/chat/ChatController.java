package com.twinkle.JakSim.controller.chat;

import com.twinkle.JakSim.model.dto.chat.ChatRoom;
import com.twinkle.JakSim.model.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("")
    public ChatRoom createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }

    @GetMapping("/list")
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
}
