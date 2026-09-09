package com.taskflow.taskflow.dto;

import java.util.Map;

public class ErrorResponse {

    Integer status;
    String message;
    Map<String,String> errors;

    public ErrorResponse(Integer status, String message, Map<String, String> errors){
        this.status=status;
        this.message=message;
        this.errors =errors;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
