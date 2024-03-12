package ru.practicum.ewm.main_service.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
public class EventShortDto {

   private String annotation;
   private CategoryDto category;
   private Long confirmedRequests;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime eventDate;
   private Long id;
   private UserShortDto initiator;
   private Boolean paid;
   private String title;
   private Integer views;

}
