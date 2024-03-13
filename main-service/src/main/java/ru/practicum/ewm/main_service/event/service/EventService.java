package ru.practicum.ewm.main_service.event.service;

import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.filter.AdminEventFilter;
import ru.practicum.ewm.main_service.filter.PublicEventFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface EventService {
   EventFullDto addNewEvent(Long userId, NewEventDto eventDto);

   List<EventFullDto> getUserEvents(long userId, int from, int size);

   EventFullDto getPublishedEvent(Long eventId);

   EventFullDto getUserEvent(Long userId, Long eventId);

   Event getUserEventUtil(long userId, long eventId);

   EventFullDto updateEventByAuthor(Long userId, Long eventId, UpdateEventUserRequest request);

   EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest eventDto);

   List<EventShortDto> getEventsPublic(PublicEventFilter filter);

   List<EventFullDto> getEventsAdmin(AdminEventFilter filter);

   List<Event> getEventsByIds(List<Long> ids);

   void recordEndpointHit(HttpServletRequest request);

   void appendStats(Event event);

   void appendStats(Collection<Event> events);
}
