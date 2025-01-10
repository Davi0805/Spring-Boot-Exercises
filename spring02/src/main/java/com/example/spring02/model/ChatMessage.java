package com.example.spring02.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage {
    private String sender;
    private String content;
    private LocalDateTime time;
    private String type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
