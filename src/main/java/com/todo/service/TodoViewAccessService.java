package com.todo.service;

import com.todo.dto.TodoAccessDto;
import com.todo.entity.Todo;
import com.todo.exception.ResourceNotFoundException;
import com.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TodoViewAccessService {

    private final TodoRepository todoRepository;

    public void addAccessUsers(TodoAccessDto todoAccessDto) {
        Todo todo = getTodoOrThrow(todoAccessDto.getTodoId());

        Set<String> existingUsers = getAccessUserSet(todo.getViewsAccessUsernames());
        existingUsers.addAll(todoAccessDto.getViewAccessToUsers());

        todo.setViewsAccessUsernames(String.join(",", existingUsers));
        todo.setUpdatedAt(LocalDateTime.now());

        todoRepository.save(todo);
    }

    public Set<String> getAllViewAccessByUsername(String username){
        String viewAccessUsernames =
                todoRepository.findViewAccessByUsernameAndDeleted(username , false);

        if(Objects.isNull(viewAccessUsernames)){
            throw new ResourceNotFoundException("View Access User Is Not added");
        }

        Set<String> result = new HashSet<>();

        convertIntoSet(viewAccessUsernames, result);

        return result;
    }
    public void removeAccessUsers(TodoAccessDto dto) {
        Todo todo = getTodoOrThrow(dto.getTodoId());

        Set<String> existingUsers = getAccessUserSet(todo.getViewsAccessUsernames());
        existingUsers.removeAll(dto.getViewAccessToUsers());

        todo.setViewsAccessUsernames(String.join(",", existingUsers));
        todo.setUpdatedAt(LocalDateTime.now());
        todoRepository.save(todo);
    }

    private Todo getTodoOrThrow(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
    }

    public Set<String> getAccessUserSet(String usernameWhoHaveViewAccess) {
        Set<String> result = new HashSet<>();

        if (usernameWhoHaveViewAccess == null || usernameWhoHaveViewAccess.trim().isEmpty()) {
            return result;
        }

        convertIntoSet(usernameWhoHaveViewAccess, result);

        return result;
    }

    private static void convertIntoSet(String usernameWhoHaveViewAccess, Set<String> result) {
        String[] parts = usernameWhoHaveViewAccess.split(",");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
    }
}
