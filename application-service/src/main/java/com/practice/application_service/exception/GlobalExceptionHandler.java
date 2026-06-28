package com.practice.application_service.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.practice.application_service.dto.response.ExceptionResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String getRequestPath(HttpServletRequest request) {
        return request != null ? request.getRequestURI() : "Unknown request path";
    }

    private String getRequestMethod(HttpServletRequest request) {
        return request != null ? request.getMethod() : "Unknown request method";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                getRequestPath(request),
                getRequestMethod(request)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "One or more fields have validation errors",
                getRequestPath(request),
                getRequestMethod(request)
        );
        response.setValidationErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message;
        if (ex.getCause() instanceof UnrecognizedPropertyException unrecognizedPropertyException) {
            message = "Unknown field: '" + unrecognizedPropertyException.getPropertyName() + "'";
        } else {
            message = "Invalid JSON format. Please check the request body syntax";
        }

        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                getRequestPath(request),
                getRequestMethod(request)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        List<String> supportedMethods = ex.getSupportedHttpMethods() != null
                ? ex.getSupportedHttpMethods().stream().map(HttpMethod::name).toList()
                : List.of();

        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                "Method " + ex.getMethod() + " is not supported for this endpoint",
                getRequestPath(request),
                getRequestMethod(request)
        );
        response.setSupportedMethods(supportedMethods);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "The requested endpoint does not exist",
                ex.getRequestURL(),
                ex.getHttpMethod()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        ExceptionResponseDTO response = new ExceptionResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred",
                getRequestPath(request),
                getRequestMethod(request)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
