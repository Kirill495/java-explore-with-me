package ru.practicum.ewm.main_service.event.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.user.model.User;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {
   private String annotation;
   private Category category;
   private Long confirmedRequests;
   private LocalDateTime createdOn;
   private String description;
   private LocalDateTime eventDate;
   @EqualsAndHashCode.Include(rank = 1000)
   private Long id;
   private User initiator;
   private Location location;
   private Boolean paid;
   private Integer participantLimit;
   private LocalDateTime publishedOn;
   private Boolean requestModeration;
   private EventState state;
   @EqualsAndHashCode.Include(rank = 100)
   private String title;
   private Integer views;
}
