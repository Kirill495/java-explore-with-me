package ru.practicum.ewm.main_service.api_admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.event.storage.service.EventService;
import ru.practicum.ewm.main_service.filter.AdminEventFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdmEventController {

   private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
   private final EventService service;

   @GetMapping
   public List<EventFullDto> getEvents(@Valid AdminEventFilter filter) {
//
      return service.getEventsAdmin(filter);
   }

   @PatchMapping(path = "/{eventId}")
   public EventFullDto updateEvent(
           @PathVariable(name = "eventId") @Positive Long eventId,
           @RequestBody @Valid UpdateEventAdminRequest eventDto) {
      return service.updateEventByAdmin(eventId, eventDto);
   }
}
