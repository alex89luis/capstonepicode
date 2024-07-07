package com.capstone.capstone.exception;

import com.capstone.capstone.model.Error;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "Content-Type '" + ex.getContentType() + "' not supported. Supported content types: " +
                ex.getSupportedMediaTypes().stream()
                        .map(MediaType::toString)
                        .collect(Collectors.joining(", "));
        Error error = new Error();
        error.setMessage(message);
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        error.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        error.setErrorCode("UNSUPPORTED_MEDIA_TYPE");
        error.setDetails(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> UnauthorizedHandler(UnauthorizedException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        error.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        error.setErrorCode("UNAUTHORIZED");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UtenteNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundHandler(UtenteNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("USER_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CarrelloNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundHandler(CarrelloNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("USER_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProdottoNotFoundException.class)
    public ResponseEntity<Object> UserNotFoundHandler(ProdottoNotFoundException e) {
        Error error = new Error();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode("USER_NOT_FOUND");
        error.setDetails(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
