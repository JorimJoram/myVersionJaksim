package com.twinkle.JakSim.config.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twinkle.JakSim.model.dto.chat.ChatMessage;
import com.twinkle.JakSim.model.dto.chat.ChatRoom;
import com.twinkle.JakSim.model.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: " + payload);
//        TextMessage textMessage = new TextMessage("Welcome to Chatting");
//        session.sendMessage(textMessage);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
    }
}
