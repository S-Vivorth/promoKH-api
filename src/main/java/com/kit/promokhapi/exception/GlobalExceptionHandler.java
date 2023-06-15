package com.kit.promokhapi.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kit.promokhapi.dto.ResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseDTO<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        Map<String, String> fieldErrorMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "Validation error", fieldErrorMap));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseDTO<?>> handleMongoException(DataAccessException ex) {
        String errorMessage = "An error occurred while accessing database.";

        if (ex.getMessage().contains("duplicate")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO<>(HttpStatus.CONFLICT.value(), "Conflict occurred", null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleException(Exception ex) {
        String errorMessage = "Error occurred - " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, null));
    }
}
