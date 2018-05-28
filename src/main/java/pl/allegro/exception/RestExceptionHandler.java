package pl.allegro.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.allegro.model.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> exceptionNoHandler(NoHandlerFoundException e,
      WebRequest request) {
    return new ResponseEntity<>(new ErrorResponse(BAD_REQUEST.value(), e.getMessage()),
        BAD_REQUEST);
  }
}