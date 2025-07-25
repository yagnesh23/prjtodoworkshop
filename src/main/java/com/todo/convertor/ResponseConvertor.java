package com.todo.convertor;

import com.todo.response.ErrorResponse;
import com.todo.response.SuccessResponse;

public class ResponseConvertor {

    public static <T> SuccessResponse createResponse(T data){
        return new SuccessResponse(data);
    }

    public static ErrorResponse createErrorResponse(String message){
        return new ErrorResponse(message);
    }


}
