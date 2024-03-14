package ru.practicum.ewm.main_service.comment.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class NewCommentException extends OperationRequirementsMismatchException {
   public NewCommentException(String message) {
      super(message);
   }
}
