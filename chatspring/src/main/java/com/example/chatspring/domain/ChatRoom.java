package com.example.chatspring.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "chat_rooms")
public class ChatRoom {
    @Id
    private String id;
    private String name;

    public ChatRoom(String name) {
        this.name = name;
    }
}

