package ru.practicum.ewm.main_service.comment.exception;

import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;

public class CommentUpdateException extends OperationRequirementsMismatchException {
   public CommentUpdateException(String message) {
      super(message);
   }
}
