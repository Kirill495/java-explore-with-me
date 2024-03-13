package ru.practicum.ewm.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class EndpointHitDto {
   private String app;
   private String uri;
   private String ip;
   private String timestamp;
}
