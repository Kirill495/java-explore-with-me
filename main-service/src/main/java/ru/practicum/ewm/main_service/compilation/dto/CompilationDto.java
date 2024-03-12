package ru.practicum.ewm.main_service.compilation.dto;

import lombok.Data;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;

import java.util.List;

@Data
public class CompilationDto {
   private Long id;
   private List<EventShortDto> content;
   private Boolean pinned;
   private String title;
}
