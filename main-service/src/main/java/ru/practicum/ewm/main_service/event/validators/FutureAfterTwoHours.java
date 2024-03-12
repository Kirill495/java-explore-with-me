package ru.practicum.ewm.main_service.event.validators;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FutureAfterTwoHoursValidator.class)
@Documented
public @interface FutureAfterTwoHours {
   String message() default "Should be after two hours from now";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}
