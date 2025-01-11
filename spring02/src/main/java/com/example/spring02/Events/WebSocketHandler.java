package com.example.spring02.Events;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.spring02.Controller.ChatController;
import com.example.spring02.Repository.ChatMessageRepository;
import com.example.spring02.model.ChatMessage;
import com.example.spring02.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public WebSocketHandler(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public boolean isUserLogged(List<User> userList, String sessionId) {
        for (User user : userList)
        {
            try {
                if (Objects.equals(user.getSessionId(), sessionId))
                    return user.isLogged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void LoginMethod(List<User> userList, WebSocketSession session, String message) throws IOException {
        if (message.startsWith("/login"))
        {
            for (User user : userList)
            {
                try {
                    if (Objects.equals(user.getSessionId(), session.getId()))
                    {
                        user.setUsername(message);
                        user.setLogged(true);
                        session.sendMessage(new TextMessage("Logado com sucesso!"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
            session.sendMessage(new TextMessage("Usuario nao logado!\n/login <username>"));
    }

    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);


    private static Set<WebSocketSession> sessions = new HashSet<>();
    private static Map<WebSocketSession, String> userNames = new HashMap<>();
    private static List<User> users = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Adicione um nome de usuário fictício para a sessão (em um caso real, você obteria isso de outra forma)
        User newUser = new User(null, session.getId());
        users.add(newUser);
        sendUserNames();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(session.getId());
        chatMessage.setContent(message.getPayload()); // Message esta em payload e nao pelo methodo toString

        chatMessageRepository.save(chatMessage);
        logger.info("[DB] Salvo: " + chatMessage.getContent());
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    if (isUserLogged(users, webSocketSession.getId()))
                        webSocketSession.sendMessage(message);
                    else
                        LoginMethod(users, session, message.getPayload());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendUserNames() {
        String userNamesMessage = userNames.values().stream().collect(Collectors.joining(", "));
        TextMessage message = new TextMessage("Connected users: " + userNamesMessage);
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        userNames.remove(session);
        sendUserNames();
    }
}
