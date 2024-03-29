package ru.practicum.ewm.main_service.event.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.filter.BaseFilter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminEventFilter extends BaseFilter {

   private List<Long> users;
   private List<EventState> states;
   private List<Long> categories;
   private LocalDateTime rangeStart;
   private LocalDateTime rangeEnd;

}
