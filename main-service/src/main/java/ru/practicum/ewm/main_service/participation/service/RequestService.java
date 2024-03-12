package ru.practicum.ewm.main_service.participation.service;

import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main_service.participation.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main_service.participation.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
   List<ParticipationRequestDto> getRequestsForCurrentUserEvent(long userId, long eventId);

   EventRequestStatusUpdateResult updateRequestStatus(long UserId, long eventId, EventRequestStatusUpdateRequest request);

   ParticipationRequestDto addRequest(Long userId, Long eventId);

   List<ParticipationRequestDto> getRequestsForOtherUserEvents(long userId);

   ParticipationRequestDto discardOwnRequestForEvent(long userId, long requestId);
}
