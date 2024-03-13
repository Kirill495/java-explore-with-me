package ru.practicum.ewm.main_service.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.main_service.category.service.CategoryService;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.event.storage.entity.RequestsToEvent;
import ru.practicum.ewm.main_service.event.exception.EventGetException;
import ru.practicum.ewm.main_service.event.exception.EventNotFoundException;
import ru.practicum.ewm.main_service.event.exception.EventUpdateException;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.model.EventSortFields;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.event.model.EventStateAction;
import ru.practicum.ewm.main_service.event.storage.repository.EventRepository;
import ru.practicum.ewm.main_service.event.storage.specification.EventSpecification;
import ru.practicum.ewm.main_service.filter.AdminEventFilter;
import ru.practicum.ewm.main_service.filter.PublicEventFilter;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
import ru.practicum.ewm.main_service.participation.storage.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.service.UserService;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

   private final EventRepository repository;
   private final CategoryService catService;
   private final UserService userService;

   private final ParticipationRequestRepository requestRepository;

   private final EventMapper eventMapper;

   private final StatsClient statsClient;

   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   private static final String APPLICATION_NAME = "ewm-main-service";

   @Override
   public EventFullDto addNewEvent(Long userId, NewEventDto eventDto) {
      User user = userService.getUserUtil(userId);
      Event event = eventMapper.toModel(eventDto);
      event.setCategory(catService.getCategoryUtil(eventDto.getCategory()));
      event.setInitiator(user);
      EventEntity eventEntity = eventMapper.toEntity(event);
      eventEntity = repository.save(eventEntity);
      return eventMapper.toFullDto(eventMapper.toModel(eventEntity));
   }

   @Override
   public List<EventFullDto> getUserEvents(long userId, Pageable page) {
      userService.getUserUtil(userId);
      Specification<EventEntity> specification = Specification
              .where(EventSpecification.ofInitiator(userId));
      List<EventEntity> eventEntities = repository.findAll(specification, page).toList();
      List<Event> events = eventMapper.toModel(eventEntities);
      appendStats(events);
      return eventMapper.toFullDto(events);
   }

   @Override
   public EventFullDto getPublishedEvent(Long eventId) {

      EventEntity entity = repository
              .findOne(EventSpecification.ofEventIdAndPublished(eventId))
              .orElseThrow(() -> new EventNotFoundException(eventId));
      Event event = eventMapper.toModel(entity);
      appendStats(event);
      return eventMapper.toFullDto(event);
   }

   @Override
   public EventFullDto getUserEvent(Long userId, Long eventId) {
      Event event = getUserEventUtil(userId, eventId);
      appendStats(event);
      return eventMapper.toFullDto(event);
   }

   @Override
   public List<Event> getEventsByIds(List<Long> ids) {
      List<Event> events = eventMapper.toModel(repository.findAllById(ids));
      appendStats(events);
      return events;
   }

   @Override
   public Event getUserEventUtil(long userId, long eventId) {
      userService.getUserUtil(userId);
      EventEntity event = repository
              .findOne(EventSpecification.ofInitiatorAndEventId(userId, eventId))
              .orElseThrow(() -> new EventNotFoundException(eventId));
      return eventMapper.toModel(event);
   }

   @Override
   public EventFullDto updateEventByAuthor(Long userId, Long eventId, UpdateEventUserRequest request) {
      userService.getUserUtil(userId);
      EventEntity entity = repository
              .findOne(EventSpecification.ofInitiatorAndEventId(userId, eventId))
              .orElseThrow(() -> new EventNotFoundException(eventId));
      Event event = eventMapper.toModel(entity);
      if (event.getState() == EventState.PUBLISHED) { //  && !event.getRequestModeration()) {
         throw new EventUpdateException("Only pending or canceled events can be changed");
      }
      updateEventByAuthorInner(event, request);
      entity = repository.save(eventMapper.toEntity(event));
      Event resEvent = eventMapper.toModel(entity);
      appendStats(resEvent);
      return eventMapper.toFullDto(resEvent);
   }

   @Override
   public EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateRequest) {

      EventEntity entity = repository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
      Event event = eventMapper.toModel(entity);
      EventStateAction action = updateRequest.getStateAction();

      if (action == EventStateAction.PUBLISH_EVENT && event.getState() != EventState.PENDING) {
         throw new EventUpdateException("Event can be published only in Pending status");
      } else if (action == EventStateAction.REJECT_EVENT && event.getState() == EventState.PUBLISHED) {
         throw new EventUpdateException("Event in PUBLISHED state can not be canceled");
      }
      updateEventByAdminInner(event, updateRequest);
      if (event.getPublishedOn() != null && !event.getEventDate().isAfter(event.getPublishedOn().plusHours(1))) {
         throw new EventUpdateException("Distance between published date and event date must be more than one hour");
      }
      entity = repository.save(eventMapper.toEntity(event));
      Event resEvent = eventMapper.toModel(entity);
      appendStats(resEvent);
      return eventMapper.toFullDto(resEvent);
   }

   @Override
   public List<EventShortDto> getEventsPublic(PublicEventFilter filter) {

      if ((filter.getRangeStart() != null) && (filter.getRangeEnd() != null)
                      && filter.getRangeStart().isAfter(filter.getRangeEnd())) {
         throw new EventGetException("end is before start");
      }
      Pageable pageable;
      if (filter.getSort() != null && filter.getSort().equals(EventSortFields.EVENT_DATE)) {
         pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize(), Sort.by("eventDate"));
      } else {
         pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
      }

      Specification<EventEntity> specification = EventSpecification.ofFilter(filter);
      List<EventEntity> eventEntities = repository.findAll(specification, pageable).getContent();
      List<Event> events = eventMapper.toModel(eventEntities);
      appendStats(events);
      if (Objects.nonNull(filter.getSort()) && filter.getSort().equals(EventSortFields.VIEWS)) {
         events.sort(Comparator.comparingInt(Event::getViews));
      }
      return eventMapper.toShortDto(events);
   }

   @Override
   public List<EventFullDto> getEventsAdmin(AdminEventFilter filter) {

      Pageable pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
      List<EventEntity> eventEntities = repository
              .findAll(EventSpecification.ofFilter(filter), pageable)
              .getContent();
      List<Event> events = eventMapper.toModel(eventEntities);
      appendStats(events);
      return eventMapper.toFullDto(events);

   }

   @Override
   public void recordEndpointHit(HttpServletRequest request) {
      EndpointHitDto endpointHit = EndpointHitDto.builder()
              .withApp(APPLICATION_NAME)
              .withIp(request.getRemoteAddr())
              .withUri(request.getRequestURI())
              .withTimestamp(LocalDateTime.now().format(FORMATTER))
              .build();

      statsClient.saveInfo(endpointHit);
   }

   public void appendStats(Event event) {
      appendStats(List.of(event));
   }

   public void appendStats(Collection<Event> events) {
      if (events.isEmpty()) {
         return;
      }
      appendViews(events);
      appendConfirmedRequests(events);
   }

   private void appendViews(Collection<Event> events) {
      if (events.isEmpty()) {
         return;
      }
      List<String> uris = events.stream()
              .map(event -> "/events/" + event.getId())
              .collect(Collectors.toList());
      List<ViewStats> stats = statsClient.fetchInfo(
              LocalDateTime.now().minusHours(5),
              LocalDateTime.now().plusHours(5),
              uris,
              true
      ).getBody();
      if (stats == null || stats.isEmpty()) {
         return;
      }
      Map<Long, Integer> views = new HashMap<>();
      for (ViewStats stat : stats) {
         long eventId = Long.parseLong(stat.getUri().substring(stat.getUri().lastIndexOf('/') + 1));
         views.put(eventId, stat.getHits().intValue());
      }
      for (Event event : events) {
         event.setViews(views.getOrDefault(event.getId(), 0));
      }
   }

   private void appendConfirmedRequests(Collection<Event> events) {
      if (events.isEmpty()) {
         return;
      }
      Collection<RequestsToEvent> countRequests = requestRepository.countRequestsToEvents(eventMapper.toEntity(events), RequestStatus.CONFIRMED);
      if (countRequests.isEmpty()) {
         return;
      }
      Map<Long, Long> counts = countRequests.stream()
              .collect(Collectors.toMap(req -> (req.getEvent().getId()), RequestsToEvent::getRequestsCount));
      for (Event event : events) {
         event.setConfirmedRequests(counts.getOrDefault(event.getId(), 0L));
      }
   }

   private void updateEventByAuthorInner(Event event, UpdateEventUserRequest request) {

      Event eventInput = eventMapper.toModel(request);

      if (request.getStateAction() == EventStateAction.SEND_TO_REVIEW) {
         eventInput.setState(EventState.PENDING);
      } else if (request.getStateAction() == EventStateAction.CANCEL_REVIEW) {
         eventInput.setState(EventState.CANCELED);
      }
      if (request.getCategory() != null) {
         eventInput.setCategory(catService.getCategoryUtil(request.getCategory()));
      }
      updateEventFields(event, eventInput);
   }

   private void updateEventByAdminInner(Event event, UpdateEventAdminRequest request) {

      Event eventInput = eventMapper.toModel(request);

      if (request.getCategory() != null) {
         eventInput.setCategory(catService.getCategoryUtil(request.getCategory()));
      }
      if (request.getStateAction() == EventStateAction.PUBLISH_EVENT) {
         eventInput.setState(EventState.PUBLISHED);
         eventInput.setPublishedOn(LocalDateTime.now());
      } else if (request.getStateAction() == EventStateAction.REJECT_EVENT) {
         eventInput.setState(EventState.CANCELED);
      }
      if (request.getCategory() != null) {
         eventInput.setCategory(catService.getCategoryUtil(request.getCategory()));
      }
      updateEventFields(event, eventInput);
   }

   private void updateEventFields(Event target, Event source) {
      Optional.ofNullable(source.getAnnotation()).ifPresent(target::setAnnotation);
      Optional.ofNullable(source.getCategory()).ifPresent(target::setCategory);
      Optional.ofNullable(source.getDescription()).ifPresent(target::setDescription);
      Optional.ofNullable(source.getEventDate()).ifPresent(target::setEventDate);
      Optional.ofNullable(source.getLocation()).ifPresent(target::setLocation);
      Optional.ofNullable(source.getPaid()).ifPresent(target::setPaid);
      Optional.ofNullable(source.getParticipantLimit()).ifPresent(target::setParticipantLimit);
      Optional.ofNullable(source.getPublishedOn()).ifPresent(target::setPublishedOn);
      Optional.ofNullable(source.getRequestModeration()).ifPresent(target::setRequestModeration);
      Optional.ofNullable(source.getState()).ifPresent(target::setState);
      Optional.ofNullable(source.getTitle()).ifPresent(target::setTitle);
   }

}
