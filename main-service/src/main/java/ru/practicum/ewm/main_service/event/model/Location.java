package ru.practicum.ewm.main_service.event.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Location {
   @NotNull
   private Float lat;
   @NotNull
   private Float lon;
}
