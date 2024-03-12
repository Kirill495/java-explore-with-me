package ru.practicum.ewm.main_service.compilation.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewCompilationDto {

   @UniqueElements
   private List<Integer> events;

   private Boolean pinned = false;

   @NotNull
   @NotBlank
   @Length(min = 1, max = 50)
   private String title;


}
