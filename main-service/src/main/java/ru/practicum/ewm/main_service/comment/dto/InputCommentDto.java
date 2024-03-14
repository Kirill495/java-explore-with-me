package ru.practicum.ewm.main_service.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputCommentDto {

   @NotBlank
   @Size(min = 10, max = 500)
   private String text;

}
