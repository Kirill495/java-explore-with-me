package ru.practicum.ewm.main_service.api_admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.dto.InputCommentDto;
import ru.practicum.ewm.main_service.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@SuppressWarnings("unused")
@Validated
@RequestMapping("/admin/comments/{id}")
@RestController
@RequiredArgsConstructor
public class AdmCommentController {

   private final CommentService service;

   @DeleteMapping
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void removeCommentByAdmin(@Positive @PathVariable long id) {
      service.removeCommentByAdmin(id);
   }

   @PatchMapping
   @ResponseStatus(HttpStatus.OK)
   public CommentDto updateCommentByAdmin(@Positive @PathVariable long id,
                                          @RequestBody @Valid InputCommentDto input) {
      return service.updateCommentByAdmin(id, input);
   }
}
