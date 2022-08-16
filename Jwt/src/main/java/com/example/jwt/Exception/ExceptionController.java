package com.example.jwt.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(value = ExistedException.class)
    public Map<String,String> hendlerExistedExcepttion(ExistedException ex){
        Map<String,String> map = new HashMap<>();
        map.put("errorMassage",ex.getMessage());
        return map;
    }
    @ExceptionHandler(value = NotFoundException.class)
    public Map<String,String> hendlerNotFound(NotFoundException ex){
        Map<String,String> map = new HashMap<>();
        map.put("errorMassage",ex.getMessage());
        return map;
    }
}
