package ru.practicum.ewm.main_service.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class EventFilterModel {
   private UserEntity author;
   private Long eventId;
}
