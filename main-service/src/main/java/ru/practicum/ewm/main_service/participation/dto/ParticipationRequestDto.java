package ru.practicum.ewm.main_service.participation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {

   private Long id;
   private Long event;
   private Long requester;
   private RequestStatus status;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime created;
}
