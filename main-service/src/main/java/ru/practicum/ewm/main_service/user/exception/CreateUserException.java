package ru.practicum.ewm.main_service.user.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class CreateUserException extends OperationRequirementsMismatchException {
   public CreateUserException(String message) {
      super(message);
   }
}
