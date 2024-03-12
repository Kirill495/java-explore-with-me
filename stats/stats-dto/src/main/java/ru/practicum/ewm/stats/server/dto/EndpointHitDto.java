package ru.practicum.ewm.stats.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class EndpointHitDto {

   private String app;
   private String uri;
   private String ip;
   private String timestamp;
}
