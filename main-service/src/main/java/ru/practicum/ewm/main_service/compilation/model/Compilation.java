package ru.practicum.ewm.main_service.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.main_service.event.model.Event;

import java.util.List;

@Data
@AllArgsConstructor
public class Compilation {
   private Long id;
   private List<Event> events;
   private Boolean pinned;
   private String title;
}
