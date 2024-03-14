package ru.practicum.ewm.main_service.comment.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class CommentNotFoundException extends ElementNotFoundException {

   private static final String DEFAULT_MESSAGE_PATTERN = "Comment with id=%d was not found.";

   public CommentNotFoundException(long id) {
      super(String.format(DEFAULT_MESSAGE_PATTERN, id));
   }
}
