package ru.practicum.ewm.stats.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.stats.server.exception.IncorrectParameterException;

@SuppressWarnings("unused")
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

   @ExceptionHandler(IncorrectParameterException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponseEntity<String> handleElementNotFoundException(final IncorrectParameterException e) {
      log.info("Got status {}", HttpStatus.NOT_FOUND, e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
   }
}
