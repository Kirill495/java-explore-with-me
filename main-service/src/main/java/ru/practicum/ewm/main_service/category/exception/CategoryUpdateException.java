package ru.practicum.ewm.main_service.category.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class CategoryUpdateException extends OperationRequirementsMismatchException {
   public CategoryUpdateException(String message) {
      super(message);
   }
}
