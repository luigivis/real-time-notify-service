package com.luigivismara.modeldomain.exception;

import com.luigivismara.modeldomain.http.HttpResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResponse<?> handleConflict(MethodArgumentNotValidException ex) {
        return new HttpResponse<>(
                HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpResponse<?> handleConstraintViolation(ConstraintViolationException ex) {
        return new HttpResponse<>(
                HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public HttpResponse<?> handleSQLIntegrityConstraintViolation(
            SQLIntegrityConstraintViolationException ex) {
        var message =
                switch (ex.getErrorCode()) {
                    case 1062 -> "Value Already Exist";
                    case 1063 -> "SYNTAX ERROR";
                    default -> ex.getMessage();
                };
        return new HttpResponse<>(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ConnectException.class)
    public HttpResponse<?> handleConflict(ConnectException ex) {
        return new HttpResponse<>(HttpStatus.SERVICE_UNAVAILABLE, "Connection refused");
    }
}
