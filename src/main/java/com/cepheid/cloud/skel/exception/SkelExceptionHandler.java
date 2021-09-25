package com.cepheid.cloud.skel.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SkelExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = ItemNotFoundException.class)
  protected ResponseEntity<Object> handleDuplicateBidderException(ItemNotFoundException e) {
    return ResponseEntity.status(NOT_FOUND)
        .body(ErrorResponse.of(String.valueOf(NOT_FOUND.value()), e.getMessage()));
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class ErrorResponse {

    private String status;
    private String reason;

    public static ErrorResponse of(String status, String reason) {
      return new ErrorResponse(status, reason);
    }

    public static ErrorResponse of(HttpStatus status, String reason) {
      return new ErrorResponse(String.valueOf(status.value()), reason);
    }
  }
}
