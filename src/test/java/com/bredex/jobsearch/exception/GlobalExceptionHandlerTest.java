package com.bredex.jobsearch.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  @Test
  void testHandleValidationExceptions() {
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null,
        new BeanPropertyBindingResult(null, "objectName"));
    exception.getBindingResult().addError(new FieldError("objectName", "fieldName", "error message"));

    Map<String, String> errors = exceptionHandler.handleValidationExceptions(exception);

    assertEquals(1, errors.size());
    assertEquals("error message", errors.get("fieldName"));
  }

  @Test
  void testHandleDataIntegrityViolationException() {
    DataIntegrityViolationException exception = new DataIntegrityViolationException("Email must be unique.");

    Map<String, String> errors = exceptionHandler.handleDataIntegrityViolationException(exception);

    assertEquals(1, errors.size());
    assertEquals("Email must be unique.", errors.get("email"));
  }
}
