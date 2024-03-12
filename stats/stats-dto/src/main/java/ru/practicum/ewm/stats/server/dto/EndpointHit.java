package ru.practicum.ewm.stats.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Locale;

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
//   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
//   @JsonFormat(pattern = "yyyy-MM-dd")
   private LocalDateTime timestamp;
}
