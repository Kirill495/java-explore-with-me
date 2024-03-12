package ru.practicum.ewm.main_service.participation.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class NewParticipationRequestException extends OperationRequirementsMismatchException {
   public NewParticipationRequestException(String message) {
      super(message);
   }
}
