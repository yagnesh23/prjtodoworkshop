package com.todo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T>{
    T data;
    String status = "Success";
    int code = 1;

    public SuccessResponse(T data) {
        this.data = data;
    }
}
