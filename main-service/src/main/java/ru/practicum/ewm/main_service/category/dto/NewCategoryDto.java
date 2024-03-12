package ru.practicum.ewm.main_service.category.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewCategoryDto {

   @NotNull
   @NotBlank
   @Length(min = 1, max = 50)
   private String name;
}
