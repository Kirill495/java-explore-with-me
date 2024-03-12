package ru.practicum.ewm.main_service.participation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Request {

   private Long id;
   private Event event;
   private User requester;
   private RequestStatus status;
   private LocalDateTime created;

}
