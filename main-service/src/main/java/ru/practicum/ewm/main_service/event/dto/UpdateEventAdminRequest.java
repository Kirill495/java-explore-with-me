package ru.practicum.ewm.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main_service.event.model.EventStateAction;
import ru.practicum.ewm.main_service.event.model.Location;
import ru.practicum.ewm.main_service.event.validators.FutureAfterTwoHours;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class UpdateEventAdminRequest {

   @Length(min = 20, max = 2000)
   private String annotation;

   private Long category;

   @Length(min = 20, max = 7000)
   private String description;

   @FutureAfterTwoHours
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime eventDate;

   private Location location;

   private Boolean paid;

   private Integer participantLimit;

   private Boolean requestModeration;

   private EventStateAction stateAction;

   @Length(min = 3, max = 120)
   private String title;
}
