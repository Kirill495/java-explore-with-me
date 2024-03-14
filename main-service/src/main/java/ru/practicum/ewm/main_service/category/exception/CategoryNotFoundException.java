package ru.practicum.ewm.main_service.category.exception;

import ru.practicum.ewm.main_service.exception.ElementNotFoundException;

public class CategoryNotFoundException extends ElementNotFoundException {
   public CategoryNotFoundException(long id) {
      super(String.format("Category with id=%d was not found", id));
   }
}
