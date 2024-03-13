package ru.practicum.ewm.main_service.exception;

public class ElementNotFoundException extends RuntimeException {
   public ElementNotFoundException(String message) {
      super(message);
   }
}
