package com.todo.controller;

import com.todo.convertor.ResponseConvertor;
import com.todo.dto.TodoDto;
import com.todo.response.SuccessResponse;
import com.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<SuccessResponse<TodoDto>> addTodo(@RequestBody  TodoDto todoDto) {
        return new ResponseEntity<>(
                new SuccessResponse(todoService.addTodo(todoDto)) , HttpStatus.CREATED
        );
    }

    @PutMapping("/id/{todoId}")
    public ResponseEntity<SuccessResponse<TodoDto>> updateTodo(@RequestBody  TodoDto todoDto ,
                                                               @PathVariable long todoId){
        return new ResponseEntity<>(
                new SuccessResponse<>(todoService.updateTodoById(todoId , todoDto)) , HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/user")
    public ResponseEntity<SuccessResponse<List<TodoDto>>> getAllTodoByUserName(@RequestParam("uName") String username){
        return new ResponseEntity<>(
                new SuccessResponse<>(todoService.getAllUserName(username)) , HttpStatus.OK
        );
    }

    @GetMapping("/id/{todoId}")
    public ResponseEntity<SuccessResponse<TodoDto>> getTodoByTodoId(@PathVariable long todoId){
        return new ResponseEntity<>(
                new SuccessResponse<>(todoService.getTodoById(todoId)) , HttpStatus.OK
        );
    }

    @DeleteMapping("/id/{todoId}")
    public ResponseEntity<SuccessResponse<String>> deleteTodoByToDoId(@PathVariable long todoId){
        todoService.deleteTodoById(todoId);
        return new ResponseEntity<>(
                new SuccessResponse<>("Data Deleted Successfully") , HttpStatus.NO_CONTENT
        );
    }

}
