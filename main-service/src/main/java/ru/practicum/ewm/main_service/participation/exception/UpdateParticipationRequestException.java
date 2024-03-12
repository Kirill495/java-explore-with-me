package ru.practicum.ewm.main_service.participation.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class UpdateParticipationRequestException extends OperationRequirementsMismatchException {
   public UpdateParticipationRequestException(String message) {
      super(message);
   }
}
