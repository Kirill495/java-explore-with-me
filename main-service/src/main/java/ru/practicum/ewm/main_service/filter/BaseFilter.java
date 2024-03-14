package ru.practicum.ewm.main_service.filter;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class BaseFilter {
   @PositiveOrZero
   private Integer from = 0;

   @Positive
   private Integer size = 10;
}
