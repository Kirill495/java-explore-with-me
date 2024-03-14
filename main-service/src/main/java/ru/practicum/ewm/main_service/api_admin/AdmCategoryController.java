package ru.practicum.ewm.main_service.api_admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.dto.NewCategoryDto;
import ru.practicum.ewm.main_service.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/admin/categories")
@Validated
@RequiredArgsConstructor
public class AdmCategoryController {

   private final CategoryService service;

   @PostMapping(consumes = "application/json")
   public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody NewCategoryDto cat) {
      return new ResponseEntity<>(service.addCategory(cat), HttpStatus.CREATED);
   }

   @PatchMapping(path = "/{catId}")
   public ResponseEntity<CategoryDto> updateCategory(
           @PathVariable(name = "catId") @Positive Long catId,
           @Valid @RequestBody NewCategoryDto cat) {
      return new ResponseEntity<>(service.updateCategory(catId, cat), HttpStatus.OK);
   }

   @DeleteMapping(path = "/{catId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void removeCategory(@PathVariable @Positive Long catId) {
      service.removeCategory(catId);
   }
}
