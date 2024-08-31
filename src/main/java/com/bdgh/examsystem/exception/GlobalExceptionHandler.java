package com.bdgh.examsystem.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bdgh.examsystem.entity.ResponseObject;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoContentException.class)
    ResponseEntity<ResponseObject> handleNoContentException(NoContentException exception) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", exception.getMessage(), null));
    }

    @ExceptionHandler(value = NotFoundException.class)
    ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", exception.getMessage(), null));
    }

    @ExceptionHandler(value = AuthException.class)
    ResponseEntity<ResponseObject> handleAuthException(AuthException exception) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", exception.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", "Lỗi đầu vào", errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseObject> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Lỗi đầu vào";
        Map<String, String> fieldErrors = Map.of();
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) rootCause;
            fieldErrors = ife.getPath().stream()
                    .collect(Collectors.toMap(
                            path -> path.getFieldName(),
                            path -> "Định dạng không đúng",
                            (existing, replacement) -> existing));
        } else {
            errorMessage += ": " + ex.getMessage();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject("error", errorMessage, fieldErrors));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handleException(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("error", "Người dùng không có quyền thực hiện", null));
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ResponseObject> handleException(Exception exception) {
        logException(exception);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Lỗi không xác định", null));
    }

    private void logException(Exception ex) {
        log.error(
                "Exception: {}. Error: {}. Cause: {}",
                ex.getClass().getName(),
                ex.getMessage(),
                (ex.getCause() != null ? ex.getCause().getMessage() : "None"));
    }
}
