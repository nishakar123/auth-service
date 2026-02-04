package com.nishakar.exception;

import com.nishakar.commons.exception.ErrorResponse;
import com.nishakar.commons.exception.TokenGenerationFailedException;
import com.nishakar.commons.exception.UserExistsException;
import com.nishakar.commons.exception.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> userExistsException(UserExistsException ex, HttpServletRequest request){
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .httpStatus(HttpStatus.CONFLICT.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> userNotFoundException(UserExistsException ex, HttpServletRequest request){
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(TokenGenerationFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> tokenGenerationFailedException(UserExistsException ex, HttpServletRequest request){
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> UsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex, HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .httpStatus(HttpStatus.FORBIDDEN.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exception(Exception ex, HttpServletRequest request){
        ErrorResponse err = ErrorResponse.builder()
                .errorMsg(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .dateAndTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(err);
    }

}
