package ru.practicum.ewm.main_service.compilation.model;

import lombok.Data;
import ru.practicum.ewm.main_service.event.model.Event;

import java.util.List;

@Data
public class Compilation {
   private Long id;
   private List<Event> content;
   private Boolean pinned;
   private String title;
}
