package br.com.rocha.apiboleto.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> applicationException(ApplicationException e, WebRequest request) {
        var response = ErrorResponse.builder()
                .error(e.getMessage())
                .timestamp(new Date())
                .code(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException e, WebRequest request) {
        var response = ErrorResponse.builder()
                .error(e.getMessage())
                .timestamp(new Date())
                .code(HttpStatus.NOT_FOUND.value())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, WebRequest request) {
        var erros = e.getFieldErrors().stream()
                .map(item -> item.getField() + " " + item.getDefaultMessage())
                .collect(Collectors.joining());

        var response = ErrorResponse.builder()
                .error(erros)
                .timestamp(new Date())
                .code(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false))
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
