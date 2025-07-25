package com.todo.mapper;

import com.todo.dto.TodoDto;
import com.todo.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TodoMapper {

    public Todo map(TodoDto todoDto){
        return update(null  , todoDto);
    }

    public Todo update( Todo todo ,TodoDto todoDto ){
        if(Objects.isNull(todo)){
            todo = new Todo();
        }

        if(Objects.nonNull(todoDto)){
            todo.setTitle(todoDto.getTitle());
            todo.setDescription(todoDto.getDescription());
            todo.setUsername(todoDto.getUsername());
        }

        return todo;
    }

    public TodoDto map(Todo todo){
        return update(null , todo);
    }

    public TodoDto update(TodoDto todoDto , Todo todo){
        if(Objects.isNull(todoDto)){
            todoDto = new TodoDto();
        }

        if(Objects.nonNull(todo)){
            todoDto.setId(todo.getId());
            todoDto.setDescription(todo.getDescription());
            todoDto.setTitle(todo.getTitle());
            todoDto.setUsername(todo.getUsername());
        }

        return  todoDto;
    }

    public List<TodoDto> map(List<Todo> todos){
        return update(null , todos);
    }

    public List<TodoDto> update(List<TodoDto> todoDtos , List<Todo> todos){

        if(Objects.isNull(todoDtos)){
            todoDtos = new ArrayList<>();
        }

        if(Objects.nonNull(todos)){
            for(Todo todo : todos){
                todoDtos.add(map(todo));
            }
        }

        return todoDtos;
    }
}
