package com.example.spring02.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;
    private long timestamp; // Novo campo para registrar o horário das mensagens

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

}
