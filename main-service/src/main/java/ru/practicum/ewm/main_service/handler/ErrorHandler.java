package ru.practicum.ewm.main_service.handler;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.main_service.event.exception.EventGetException;
import ru.practicum.ewm.main_service.exception.ElementNotFoundException;
import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;
import ru.practicum.ewm.main_service.handler.dto.ApiError;

@SuppressWarnings("unused")
@Slf4j
@RestControllerAdvice
public class ErrorHandler {

   private static final String NOT_FOUND_REASON = "The required object is not found.";
   private static final String INVALID_REQUEST_REASON = "Incorrectly made request.";
   private static final String CONFLICT_CONSTRAINT_REASON = "Integrity constraint has been violated.";
   private static final String CONFLICT_OPERATION_REASON = "For the requested operation the conditions are not met.";
   private static final String BAD_REQUEST_REASON = "Error in request string.";

   @ExceptionHandler(ElementNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ApiError handleElementNotFoundException(final ElementNotFoundException e) {
      log.info("Got status {}", HttpStatus.NOT_FOUND, e);
      return new ApiError(e.getMessage(), NOT_FOUND_REASON, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler(EventGetException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ApiError handleElementNotFoundException(final EventGetException e) {
      log.info("Got status {}", HttpStatus.BAD_REQUEST, e);
      return new ApiError(e.getMessage(), BAD_REQUEST_REASON, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ApiError handleConstraintViolationException(final MethodArgumentNotValidException e) {
      log.info("Got status {}", HttpStatus.NOT_FOUND, e);
      String message;
      FieldError error = e.getFieldError();
      if (error != null) {
         message = String.format("Field: %s. Error: %s. Value: %s", error.getField(), error.getDefaultMessage(), error.getRejectedValue());
      } else {
         message = e.getMessage();
      }
      return new ApiError(message, INVALID_REQUEST_REASON, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(RuntimeException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ApiError handleRuntimeException(final RuntimeException e) {
      log.info("Got status {}", HttpStatus.NOT_FOUND, e);
      return new ApiError(e.getMessage(), INVALID_REQUEST_REASON, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({ConstraintViolationException.class})
   @ResponseStatus(HttpStatus.CONFLICT)
   public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
      log.info("Got status {}", HttpStatus.BAD_REQUEST, e);
      String message = String.format("%s; SQL [%s]; constraint [%s], %s",
              e.getMessage(), e.getSQL(), e.getConstraintName(), e.getSQLException().getMessage());
      return new ApiError(message, CONFLICT_CONSTRAINT_REASON, HttpStatus.CONFLICT);
   }

   @ExceptionHandler({OperationRequirementsMismatchException.class})
   @ResponseStatus(HttpStatus.CONFLICT)
   public ApiError handleOperationRequirementsMismatchException(final OperationRequirementsMismatchException e) {
      log.info("Got status {}", HttpStatus.CONFLICT, e);
      return new ApiError(e.getMessage(), CONFLICT_OPERATION_REASON, HttpStatus.CONFLICT);
   }

}
