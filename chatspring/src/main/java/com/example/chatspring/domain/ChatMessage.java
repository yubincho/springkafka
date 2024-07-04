package com.example.chatspring.domain;

import com.example.chatspring.domain.ChatRoom;
import com.example.chatspring.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "chat_messages")
public class ChatMessage {
    @Id
    private String id;
    private String sessionId;
    private String content;
    @DBRef
    private User user;
    @DBRef
    private ChatRoom chatRoom;

    public ChatMessage(String sessionId, String content, User user, ChatRoom chatRoom) {
        this.sessionId = sessionId;
        this.content = content;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
