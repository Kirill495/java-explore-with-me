package ru.practicum.ewm.main_service.event.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestsToEvent {
   private EventEntity event;
   private Long requestsCount;
}
