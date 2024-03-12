package ru.practicum.ewm.main_service.category.storage.service;

import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.dto.NewCategoryDto;
import ru.practicum.ewm.main_service.category.model.Category;

import java.util.List;

public interface CategoryService {
   CategoryDto addCategory(NewCategoryDto cat);

   CategoryDto updateCategory(Long id, NewCategoryDto cat);

   void removeCategory(Long id);

   List<CategoryDto> getCategories(int from, int size);

   CategoryDto getCategory(Long catId);

   Category getCategoryUtil(Long catId);
}
