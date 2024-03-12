package ru.practicum.ewm.main_service.event.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FutureAfterTwoHoursValidator implements ConstraintValidator<FutureAfterTwoHours, LocalDateTime> {

   private static final int DELAY_HOURS = 2;

   @Override
   public void initialize(FutureAfterTwoHours constraintAnnotation) {
   }

   @Override
   public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
      return value == null || value.isAfter(LocalDateTime.now().plusHours(DELAY_HOURS));
   }
}
