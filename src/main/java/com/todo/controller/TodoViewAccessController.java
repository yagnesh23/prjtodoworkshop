package com.todo.controller;

import com.todo.dto.TodoAccessDto;
import com.todo.response.SuccessResponse;
import com.todo.service.TodoViewAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/todo/view/access")
public class TodoViewAccessController {

    private final TodoViewAccessService todoViewAccessService;

    @PostMapping
    public ResponseEntity<SuccessResponse<String>> addTodoViewAccess(@RequestBody TodoAccessDto todoAccessDto){
        todoViewAccessService.addAccessUsers(todoAccessDto);
        return new ResponseEntity<>(new SuccessResponse<>("View Added Successfully for this todo ") , HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<String>> deleteTodoViewAccess(@RequestBody TodoAccessDto todoAccessDto){
        todoViewAccessService.removeAccessUsers(todoAccessDto);
        return new ResponseEntity<>(new SuccessResponse<>("View Deleted for this todo ") , HttpStatus.NO_CONTENT);
    }
}
