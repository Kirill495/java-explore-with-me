package ru.practicum.ewm.main_service.exception;

public class OperationRequirementsMismatchException extends RuntimeException {
   public OperationRequirementsMismatchException(String message) {
      super(message);
   }
}
