package ru.practicum.ewm.main_service.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
   private Long id;
   private String name;
   private String email;
}
