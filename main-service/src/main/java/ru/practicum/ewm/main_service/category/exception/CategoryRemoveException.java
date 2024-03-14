package ru.practicum.ewm.main_service.category.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class CategoryRemoveException extends OperationRequirementsMismatchException {
   public CategoryRemoveException(String message) {
      super(message);
   }
}
