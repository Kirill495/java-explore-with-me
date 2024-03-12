package ru.practicum.ewm.main_service.api_public;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.storage.service.CategoryService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PubCategoryController {

   private final CategoryService service;

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public List<CategoryDto> getCategories(
           @RequestParam(name = "from", defaultValue = "0") int from,
           @RequestParam(name = "size", defaultValue = "10") int size) {
      return service.getCategories(from, size);
   }

   @GetMapping(path = "{catId}")
   @ResponseStatus(HttpStatus.OK)
   public CategoryDto getCategory(
           @PathVariable(name = "catId") @Positive long catId) {
      return service.getCategory(catId);
   }

}
