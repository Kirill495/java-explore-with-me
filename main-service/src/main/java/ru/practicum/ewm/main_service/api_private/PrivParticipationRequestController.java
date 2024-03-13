package ru.practicum.ewm.main_service.api_private;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.participation.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivParticipationRequestController {
   private final RequestService requestService;

   @ResponseStatus(HttpStatus.CREATED)
   @PostMapping
   public ParticipationRequestDto addNewRequest(
           @PathVariable(name = "userId") @Positive Long ownerId,
           @RequestParam(name = "eventId") @Positive Long eventId) {
      return requestService.addRequest(ownerId, eventId);
   }

   @ResponseStatus(HttpStatus.OK)
   @GetMapping
   public List<ParticipationRequestDto> getRequestsForOtherUserEvents(
           @PathVariable(name = "userId") @Positive long userId) {
      return requestService.getRequestsForOtherUserEvents(userId);
   }

   @ResponseStatus(HttpStatus.OK)
   @PatchMapping(path = "/{requestId}/cancel")
   public ParticipationRequestDto cancelOwnRequestForEvent(
           @PathVariable(name = "userId") @Positive long userId,
           @PathVariable(name = "requestId") @Positive long requestId) {
      return requestService.cancelOwnRequestForEvent(userId, requestId);
   }
}
