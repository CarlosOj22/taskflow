package com.taskflow.taskflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import com.taskflow.taskflow.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.core.JacksonException.Reference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Java class for handle differents exceptions.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //Spring give to the method that handle the excepction
    public ResponseEntity<ErrorResponse> failValidations(MethodArgumentNotValidException ex){
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        Map<String,String> errorMap = new HashMap<>();
        for (FieldError error : errorList) {
           errorMap.put(error.getField(),error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validations failed",
                errorMap
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> failEnum(HttpMessageNotReadableException ex){
            Map<String,String> errorMap = new HashMap<>();
            Throwable cause= ex.getCause();
            if(cause instanceof InvalidFormatException){
                InvalidFormatException invalidFormat = (InvalidFormatException) cause;
                Class<?> errorClass = invalidFormat.getTargetType();
                if (errorClass.isEnum()) {
                    Object[] acceptedValues = errorClass.getEnumConstants();
                    String accepted = Arrays.toString(acceptedValues);
                    List<Reference> path = invalidFormat.getPath();
                    if (!path.isEmpty()) {

                        String field = path.get(path.size() - 1).getPropertyName();

                        String invalidValue = String.valueOf(invalidFormat.getValue());

                        errorMap.put(field, "Invalid value '" + invalidValue +
                                "'. Accepted values: " + accepted
                        );
                    }
                }
            }
        if (errorMap.isEmpty()) {
            errorMap.put("error", "General Error in JSON");
        }
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Values Failed",
                errorMap
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
