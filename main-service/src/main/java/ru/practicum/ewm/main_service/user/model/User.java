package ru.practicum.ewm.main_service.user.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
   @EqualsAndHashCode.Include(rank = 1000)
   private Long id;
   private String name;
   @EqualsAndHashCode.Include(rank = 2000)
   private String email;
}
