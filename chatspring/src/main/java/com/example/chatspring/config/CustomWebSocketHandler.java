package com.example.chatspring.config;

import com.example.chatspring.domain.ChatMessage;
import com.example.chatspring.domain.ChatRoom;
import com.example.chatspring.domain.User;
import com.example.chatspring.producer.Producer;
import com.example.chatspring.repository.ChatMessageRepository;
import com.example.chatspring.repository.ChatRoomRepository;
import com.example.chatspring.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static LinkedHashSet<WebSocketSession> sessions = new LinkedHashSet<>();

    private final Producer producer;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ObjectMapper objectMapper;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        Long userId = jsonNode.get("userId").asLong();
        String chatRoomId = jsonNode.get("chatRoomId").asText();
        String messageContent = jsonNode.get("content").asText();

        boolean isSessionAlive = sessions.contains(session);

        User user = userRepository.findById(String.valueOf(userId))
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                    .orElseThrow(() -> new NoSuchElementException("ChatRoom not found with id: " + chatRoomId));

        if (isSessionAlive) {
            producer.produceMessage("kafkaTest", messageContent);
            chatMessageRepository.save(new ChatMessage(session.getId(), payload, user, chatRoom));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendMessage(String payload) throws Exception {
        for (WebSocketSession session : sessions) {
            TextMessage msg = new TextMessage(payload);
            session.sendMessage(msg);
        }
    }
}
