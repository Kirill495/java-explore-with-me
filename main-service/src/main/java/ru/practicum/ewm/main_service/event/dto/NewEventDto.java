package ru.practicum.ewm.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main_service.event.model.Location;
import ru.practicum.ewm.main_service.event.validators.FutureAfterTwoHours;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Data
public class NewEventDto {

   @NotNull
   @NotBlank
   @Length(min = 20, max = 2000)
   private String annotation;

   @NotNull
   private Long category;

   @NotNull
   @NotBlank
   @Length(min = 20, max = 7000)
   private String description;

   @NotNull
   @FutureAfterTwoHours
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime eventDate;

   @NotNull
   private Location location;

   private Boolean paid = false;

   @PositiveOrZero
   private Integer participantLimit = 0;

   private Boolean requestModeration = true;

   @NotNull
   @NotBlank
   @Length(min = 3, max = 120)
   private String title;

}
