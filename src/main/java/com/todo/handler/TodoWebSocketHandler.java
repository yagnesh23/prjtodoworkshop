package com.todo.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.dto.TodoDto;
import com.todo.entity.Todo;
import com.todo.service.TodoService;
import com.todo.service.TodoViewAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TodoWebSocketHandler extends TextWebSocketHandler {

    private final TodoViewAccessService todoViewAccessService;

    private final TodoService todoService;
    private final ObjectMapper objectMapper;

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = getUsernameFromQuery(session.getUri().getQuery());

        if (username != null) {
            WebSocketSession sessions = userSessions.get(username);

            if (sessions == null) {
                userSessions.put(username, session);
            }

        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        TodoDto todoDto = objectMapper.readValue(message.getPayload(), TodoDto.class);

        Todo todo = todoService.getTodoByTodoId(todoDto.getId());

        Set<String> viewAccessUsernames =todoViewAccessService.getAccessUserSet(todo.getViewsAccessUsernames());

        todoService.updateTodoById(todoDto.getId() , todoDto);

        Iterator<String> iterator = viewAccessUsernames.iterator();

        while (iterator.hasNext()) {
            String user = iterator.next();
            WebSocketSession sessions = userSessions.get(user);

            if (sessions != null) {
                if (sessions.isOpen()) {
                    sessions.sendMessage(new TextMessage(objectMapper.writeValueAsString(todoDto)));
                }
            }
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = getUsernameFromQuery(session.getUri().getQuery());

        WebSocketSession webSocketSession = userSessions.get(username);
        userSessions.remove(username);
    }

    private String getUsernameFromQuery(String query) {
        if (query == null) return null;
        String[] params = query.split("&");
        for (int i = 0; i < params.length; i++) {
            if (params[i].startsWith("username=")) {
                String[] keyValue = params[i].split("=");
                if (keyValue.length == 2) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }

}
