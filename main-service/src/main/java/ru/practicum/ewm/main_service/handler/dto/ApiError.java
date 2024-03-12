package ru.practicum.ewm.main_service.handler.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class ApiError {
   private String message;
   private String reason;
   private String status;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
   private LocalDateTime timestamp = LocalDateTime.now();

   public ApiError(String message, String reason, HttpStatus status) {
      this(message, reason, status.getReasonPhrase(), LocalDateTime.now());
   }
}
