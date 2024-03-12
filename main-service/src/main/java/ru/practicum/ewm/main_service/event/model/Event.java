package ru.practicum.ewm.main_service.event.model;

import lombok.Data;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.user.model.User;

import java.time.LocalDateTime;

@Data
public class Event {

   private String annotation;
   private Category category;
   private Long confirmedRequests;
   private LocalDateTime createdOn;
   private String description;
   private LocalDateTime eventDate;
   private Long id;
   private User initiator;
   private Location location;
   private Boolean paid;
   private Integer participantLimit;
   private LocalDateTime publishedOn;
   private Boolean requestModeration;
   private EventState state;
   private String title;
   private Integer views;
}
