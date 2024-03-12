package ru.practicum.ewm.main_service.participation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main_service.event.exception.EventNotFoundException;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.event.entity.EventEntity;
import ru.practicum.ewm.main_service.event.storage.repository.EventRepository;
import ru.practicum.ewm.main_service.event.storage.service.EventService;
import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main_service.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.participation.exception.NewParticipationRequestException;
import ru.practicum.ewm.main_service.participation.exception.RequestNotFoundException;
import ru.practicum.ewm.main_service.participation.exception.UpdateParticipationRequestException;
import ru.practicum.ewm.main_service.participation.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main_service.participation.model.Request;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
import ru.practicum.ewm.main_service.participation.storage.entity.RequestEntity;
import ru.practicum.ewm.main_service.participation.storage.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main_service.participation.storage.specification.RequestSpecs;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.storage.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

   private final ParticipationRequestRepository requestRepository;
   private final EventRepository eventRepository;
   private final UserService userService;
   private final EventService eventService;
   private final ParticipationRequestMapper mapper;
   private final EventMapper eventMapper;
   private final UserMapper userMapper;

   @Override
   public ParticipationRequestDto discardOwnRequestForEvent(long userId, long requestId) {
      User requester = userService.getUserUtil(userId);

      RequestEntity requestEntity = requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));

      Request request = mapper.toModel(requestEntity);
      if (request.getStatus() != RequestStatus.PENDING) {
         throw new RequestNotFoundException("cannot discard request not in pending status");
      }
      if (request.getRequester() != requester) {
         throw new RequestNotFoundException(
                 String.format("user with id=%d is now requester to request with id=%d", userId, requestId));
      }
      request.setStatus(RequestStatus.REJECTED);
      RequestEntity result = requestRepository.save(mapper.toEntity(request));
      return mapper.toDto(mapper.toModel(result));
   }

   /*
   Получение информации о запросах на участие в событии текущего пользователя
    */
   @Override
   public List<ParticipationRequestDto> getRequestsForCurrentUserEvent(long userId, long eventId) {
      Event event = eventService.getUserEventUtil(userId, eventId);
      EventEntity eventEntity = eventMapper.toEntity(event);
      Specification<RequestEntity> spec = Specification
              .where(RequestSpecs.ofEvent(eventEntity));
      List<RequestEntity> requestEntities = requestRepository.findAll(spec);

      return mapper.toDto(mapper.toModel(requestEntities));
   }

   /*
      Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    */
   @Override
   public EventRequestStatusUpdateResult updateRequestStatus(long userId, long eventId, EventRequestStatusUpdateRequest updateRequest) {

      EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(new ArrayList<>(), new ArrayList<>());

      List<Request> requests = mapper.toModel(requestRepository.findAllById(updateRequest.getRequestIds()));
      Event event = eventService.getUserEventUtil(userId, eventId);
      if (event.getParticipantLimit() == 0) {
         return result;
      }

      if (updateRequest.getStatus() == RequestStatus.CONFIRMED && event.getParticipantLimit() < event.getConfirmedRequests().intValue() + requests.size()) {
         throw new UpdateParticipationRequestException("для события достигнут лимит по заявкам");
      }
      if (requests.stream().anyMatch(request -> (request.getStatus() != RequestStatus.PENDING))) {
         throw new UpdateParticipationRequestException("Статус можно изменить только у заявок в ожидании");
      }

      for (Request req : requests) {
         req.setStatus(updateRequest.getStatus());
      }
      requestRepository.saveAll(mapper.toEntity(requests));

      if (updateRequest.getStatus() == RequestStatus.CONFIRMED) {
         result.setConfirmedRequests(mapper.toDto(requests));
         Specification<RequestEntity> specs = Specification
                 .where(RequestSpecs.ofEvent(eventMapper.toEntity(event)))
                 .and(RequestSpecs.ofStatus(RequestStatus.CONFIRMED));
         Long confirmedRequests = requestRepository.count(specs);
         if (event.getParticipantLimit() != 0 && confirmedRequests.intValue() == event.getParticipantLimit()) {
            Specification<RequestEntity> specPendingRequests = Specification
                    .where(RequestSpecs.ofEvent(eventMapper.toEntity(event)))
                    .and(RequestSpecs.ofStatus(RequestStatus.PENDING));
            List<RequestEntity> pendingRequest = requestRepository.findAll(specPendingRequests);
            if (!pendingRequest.isEmpty()) {
               for (RequestEntity reqEntity : pendingRequest) {
                  reqEntity.setStatus(RequestStatus.REJECTED);
               }
               requestRepository.saveAll(pendingRequest);
               result.setRejectedRequests(mapper.toDto(mapper.toModel(pendingRequest)));
            }
         }

      } else {
         result.setRejectedRequests(mapper.toDto(requests));

      }
      return result;
   }

   /*
   Добавление запроса от текущего пользователя на участие в событии
    */
   @Override
   public ParticipationRequestDto addRequest(Long userId, Long eventId) {

      User currentUser = userService.getUserUtil(userId);
      EventEntity eventEntity = eventRepository.findById(eventId).orElseThrow(() -> (new EventNotFoundException(eventId)));
      Event event = eventMapper.toModel(eventEntity);
      if (Objects.equals(userId, event.getInitiator().getId())) {
         throw new NewParticipationRequestException("Нельзя создавать заявки на участие в своих событиях");
      }

      Specification<RequestEntity> spec = Specification
              .where(RequestSpecs.ofRequester(userMapper.toEntity(currentUser)))
              .and(RequestSpecs.ofEvent(eventEntity));
      if (requestRepository.exists(spec)) {
         throw new NewParticipationRequestException("Нельзя создавать повторные запросы на события");
      }
      if (event.getState() != EventState.PUBLISHED) {
         throw new NewParticipationRequestException("Нельзя участвовать в неопубликованном событии");
      }
      spec = Specification
              .where(RequestSpecs.ofEvent(eventEntity))
              .and(Specification.not(RequestSpecs.ofStatus(RequestStatus.REJECTED)));
      long curRequests = requestRepository.count(spec);
      if (event.getParticipantLimit() != 0 && curRequests == (long) event.getParticipantLimit()) {
         throw new NewParticipationRequestException("Достигнут лимит запросов на участие");
      }
      Request request = new Request(
              null,
              event,
              currentUser,
              (event.getRequestModeration() && event.getParticipantLimit() > 0) ? RequestStatus.PENDING : RequestStatus.CONFIRMED,
              null);

      RequestEntity entity = requestRepository.save(mapper.toEntity(request));
      return mapper.toDto(mapper.toModel(entity));
   }


   /*
   * Получение информации о заявках текущего пользователя на участие в чужих событиях
   */
   @Override
   public List<ParticipationRequestDto> getRequestsForOtherUserEvents(long userId) {
      User requester = userService.getUserUtil(userId);
      Specification<RequestEntity> spec = Specification.where(RequestSpecs.ofRequester(userMapper.toEntity(requester)));
      List<RequestEntity> requestEntities = requestRepository.findAll(spec);
      return mapper.toDto(mapper.toModel(requestEntities));
   }
}
