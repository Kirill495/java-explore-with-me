package ru.practicum.ewm.main_service.compilation.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class CompilationNotFoundException extends ElementNotFoundException {
   public CompilationNotFoundException(Long id) {
      super(String.format("Compilation with id=%d was not found", id));
   }
}
