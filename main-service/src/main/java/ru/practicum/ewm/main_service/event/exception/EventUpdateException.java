package ru.practicum.ewm.main_service.event.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class EventUpdateException extends OperationRequirementsMismatchException {
   public EventUpdateException(String message) {
      super(message);
   }
}
