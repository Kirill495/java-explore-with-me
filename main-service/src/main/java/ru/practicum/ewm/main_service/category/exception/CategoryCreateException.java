package ru.practicum.ewm.main_service.category.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class CategoryCreateException extends OperationRequirementsMismatchException {
   public CategoryCreateException(String message) {
      super(message);
   }
}
