package ru.practicum.ewm.main_service.participation.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class RequestNotFoundException extends ElementNotFoundException {
   public RequestNotFoundException(String message) {
      super(message);
   }

   public RequestNotFoundException(Long id) {
      super(String.format("Request with id=%d was not found", id));
   }
}
