package com.example.spring02.Controller;

import com.example.spring02.Repository.ChatMessageRepository;
import com.example.spring02.exception.ResourceNotFoundException;
import com.example.spring02.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatHistory {
    @Autowired
    ChatMessageRepository ChatCursor;

    @RequestMapping("/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory()
    {
        List<ChatMessage> data = ChatCursor.findAll();
        if (data.isEmpty())
            throw new ResourceNotFoundException("Nenhuma mensagem encontrada!");
        return ResponseEntity.ok(data); // Lembrar de setar content type para application
    }
}
