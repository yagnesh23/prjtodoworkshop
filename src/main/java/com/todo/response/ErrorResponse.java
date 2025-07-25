package com.todo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String status = "Fail";
    private int code = 0 ;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
