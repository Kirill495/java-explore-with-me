package ru.practicum.ewm.main_service.participation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EventRequestStatusUpdateResult {
   List<ParticipationRequestDto> confirmedRequests;
   List<ParticipationRequestDto> rejectedRequests;
}
