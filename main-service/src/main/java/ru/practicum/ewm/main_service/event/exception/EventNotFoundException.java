package ru.practicum.ewm.main_service.event.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class EventNotFoundException extends ElementNotFoundException {
   public EventNotFoundException(Long id) {
      super(String.format("Event with id=%d was not found", id));
   }
}
