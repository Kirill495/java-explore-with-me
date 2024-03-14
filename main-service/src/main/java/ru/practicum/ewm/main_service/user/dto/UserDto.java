package ru.practicum.ewm.main_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
   private Long id;
   private String name;
   private String email;
}
