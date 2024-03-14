package ru.practicum.ewm.main_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInputDto {
   @NotNull
   @NotBlank
   @Length(min = 2, max = 250)
   private String name;

   @NotNull
   @NotBlank
   @Length(min = 6, max = 254)
   @Email
   private String email;
}
