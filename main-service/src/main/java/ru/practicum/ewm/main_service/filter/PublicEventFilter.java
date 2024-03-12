package ru.practicum.ewm.main_service.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.main_service.event.model.EventSortFields;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicEventFilter extends BaseFilter {
   private String text;
   private List<Long> categories;
   private Boolean paid;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime rangeStart;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime rangeEnd;
   private Boolean onlyAvailable;
   private EventSortFields sort;
}
