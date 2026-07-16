package com.example.demo.exceptionhandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
        handleResourceNotFoundException(
            ResourceNotFoundException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.NOT_FOUND
        );
    }

    // 409 Conflict
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse>
        handleDuplicateResourceException(
            DuplicateResourceException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            exception.getMessage(),
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.CONFLICT
        );
    }

    // 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
        handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            LocalDateTime.now(),
            request.getRequestURI()
        );

        Map<String, String> validationErrors = new LinkedHashMap<>();
        exception.getBindingResult()
            .getFieldErrors()
            .forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));
        errorResponse.setValidationErrors(validationErrors);

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({BadRequestException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception, HttpServletRequest request) {
        // String message = exception instanceof HttpMessageNotReadableException
        //         ? "Request body is invalid or contains an unsupported value"
        //         : exception.getMessage();
        String message = "Request body is invalid or contains an unsupported value"+
                (exception instanceof BadRequestException ? ": " + exception.getMessage() : "");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message,
                LocalDateTime.now(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
        handleGenericException(
            Exception exception,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            LocalDateTime.now(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
