package com.hanghae.wisely.exception;

import com.hanghae.wisely.dto.response.BasicResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public BasicResponseDto handle(BadRequestException e) {
        return new BasicResponseDto(e.getMessage(),false);
    }
}
