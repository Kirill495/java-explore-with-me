package ru.practicum.ewm.main_service.event.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestsToEvent {
   private EventEntity event;
   private Long requestsCount;
}
