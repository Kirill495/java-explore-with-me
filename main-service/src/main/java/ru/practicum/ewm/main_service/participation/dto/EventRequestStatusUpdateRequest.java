package ru.practicum.ewm.main_service.participation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EventRequestStatusUpdateRequest {
   @NotNull
   @NotEmpty
   List<Long> requestIds;

   @NotNull
   RequestStatus status;
}
