package ru.practicum.ewm.stats.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class EndpointHit {

   private Integer id;

   @NotBlank
   private String app;

   @NotNull
   @NotBlank
   private String uri;

   @NotBlank
   private String ip;

   @NotBlank
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime timestamp;
}
