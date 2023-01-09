package com.korit.library.web.advice;

import com.korit.library.exception.CustomValidationException;
import com.korit.library.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<?> validationError(CustomValidationException e) {
        return ResponseEntity.badRequest()
                .body(new CMRespDto<>(HttpStatus.BAD_REQUEST.value(), "Validation Error", e.getErrorMap()));
    }
}
