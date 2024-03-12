package ru.practicum.ewm.main_service.user.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class UserNotFoundException extends ElementNotFoundException {
   public UserNotFoundException(long userId) {
      super(String.format("User with id=%d was not found", userId));
   }
}
