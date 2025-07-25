package com.todo.service;

import com.todo.dto.TodoDto;
import com.todo.entity.Todo;
import com.todo.exception.ResourceFoundException;
import com.todo.exception.ResourceNotFoundException;
import com.todo.mapper.TodoMapper;
import com.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public TodoDto addTodo(TodoDto todoDto){
//        validateTitleExist(todoDto.getTitle() , todoDto.getUsername());

        Todo todo = todoMapper.map(todoDto);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setDeleted(false);

        todo = todoRepository.save(todo);

        return todoMapper.map(todo);
    }

    public List<TodoDto> getAllUserName(String username){
        List<Todo> todos  =
                todoRepository.findByTodoByUserName(username , false);

        return todoMapper.map(todos);
    }

    public TodoDto getTodoById(long id){
        Todo todo  =
                todoRepository.findById(id , false);

        if(Objects.isNull(todo)){
            throw new ResourceNotFoundException("Todo Details not found with this id");
        }

        return todoMapper.map(todo);
    }

    public Todo getTodoByTodoId(long id){
        Todo todo  =
                todoRepository.findById(id , false);

        return todo;
    }

    public TodoDto updateTodoById(Long id , TodoDto todoDto){
        Todo todo  =
                todoRepository.findById(id , false);

        todoMapper.update(todo , todoDto);
        todo.setUpdatedAt(LocalDateTime.now());

        todoRepository.save(todo);

        return todoMapper.map(todo);
    }

    public void deleteTodoById(long id){
        Todo todo  =
                todoRepository.findById(id , false);

        if(Objects.isNull(todo)){
            throw new ResourceNotFoundException("Todo Details not found with this id");
        }

        todo.setDeleted(true);
        todo.setDeleteAt(LocalDateTime.now());

        todoRepository.save(todo);

    }
    private void validateTitleExist(String title , String username){
         boolean alreadyExist = todoRepository.existsByTitleAndUsernameAndDeletedFalse(title , username);

         if (alreadyExist){
             throw new ResourceFoundException("Already this title is use please change title ");
         }

    }
}
