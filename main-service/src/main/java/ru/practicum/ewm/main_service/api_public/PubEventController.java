package ru.practicum.ewm.main_service.api_public;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.storage.service.EventService;
import ru.practicum.ewm.main_service.filter.PublicEventFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PubEventController {

   private final EventService service;

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public List<EventShortDto> getEvents(
           @Valid PublicEventFilter filter,
           HttpServletRequest request) {
      service.recordEndpointHit(request);
      return service.getEventsPublic(filter);
   }

   @GetMapping(path = "/{eventId}")
   @ResponseStatus(HttpStatus.OK)
   public EventFullDto getEvents(
           @PathVariable @Positive Long eventId,
           HttpServletRequest request) {
      service.recordEndpointHit(request);
      return service.getPublishedEvent(eventId);
   }

}
