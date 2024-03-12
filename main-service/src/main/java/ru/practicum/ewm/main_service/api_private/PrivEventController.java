package ru.practicum.ewm.main_service.api_private;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.main_service.event.storage.service.EventService;
import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main_service.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.participation.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivEventController {

   private final EventService eventService;
   private final RequestService requestService;

   @ResponseStatus(HttpStatus.CREATED)
   @PostMapping
   public EventFullDto addNewEvent(@PathVariable(name = "userId") @Positive Long userId,
                                   @Valid @RequestBody NewEventDto eventDto) {
      return eventService.addNewEvent(userId, eventDto);
   }

   @ResponseStatus(HttpStatus.OK)
   @GetMapping
   public List<EventFullDto> getUserEvents(
           @PathVariable(name = "userId") @Positive Long userId,
           @RequestParam(name = "from", defaultValue = "0") int from,
           @RequestParam(name = "size", defaultValue = "10") int size) {
      return eventService.getUserEvents(userId, from, size);
   }

   @ResponseStatus(HttpStatus.OK)
   @GetMapping(path = "/{eventId}")
   public EventFullDto getUserEvent(
           @PathVariable(name = "userId") @Positive Long userId,
           @PathVariable(name = "eventId") @Positive Long eventId) {
      return eventService.getUserEvent(userId, eventId);
   }

   @ResponseStatus(HttpStatus.OK)
   @PatchMapping(path = "/{eventId}")
   public EventFullDto updateEvent(
           @PathVariable(name = "userId") @Positive Long userId,
           @PathVariable(name = "eventId") @Positive Long eventId,
           @Valid @RequestBody UpdateEventUserRequest eventDto) {
      return eventService.updateEventByAuthor(userId, eventId, eventDto);
   }

   /*
   Получение информации о запросах на участие в событии текущего пользователя
    */
   @ResponseStatus(HttpStatus.OK)
   @GetMapping(path = "/{eventId}/requests")
   public List<ParticipationRequestDto> getRequestsForEvent(
           @PathVariable(name = "userId") @Positive long userId,
           @PathVariable(name = "eventId") @Positive long eventId) {
      return requestService.getRequestsForCurrentUserEvent(userId, eventId);
   }

   /*
   Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    */
   @ResponseStatus(HttpStatus.OK)
   @PatchMapping(path = "/{eventId}/requests")
   public EventRequestStatusUpdateResult updateRequestStatus(
           @PathVariable(name = "userId") @Positive long authorId,
           @PathVariable(name = "eventId") @Positive long eventId,
           @Valid @RequestBody EventRequestStatusUpdateRequest request) {
      return requestService.updateRequestStatus(authorId, eventId, request);
   }
}
