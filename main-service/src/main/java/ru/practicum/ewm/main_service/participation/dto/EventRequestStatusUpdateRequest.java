package ru.practicum.ewm.main_service.participation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EventRequestStatusUpdateRequest {
   @NotNull
   @NotEmpty
   List<Long> requestIds;

   @NotNull
   RequestStatus status;
}
