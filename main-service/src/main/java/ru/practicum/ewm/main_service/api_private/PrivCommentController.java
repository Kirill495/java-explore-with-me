package ru.practicum.ewm.main_service.api_private;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.dto.CommentShortDto;
import ru.practicum.ewm.main_service.comment.dto.InputCommentDto;
import ru.practicum.ewm.main_service.comment.service.CommentService;
import ru.practicum.ewm.main_service.comment.filter.CommentFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@SuppressWarnings("unused")
@Validated
@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
public class PrivCommentController {

   private final CommentService service;

   /*
   Создание нового комментария пользователем
    */
   @ResponseStatus(HttpStatus.CREATED)
   @PostMapping(path = "/events/{eventId}")
   public CommentDto addNewComment(
           @PathVariable("userId") @Positive Long authorId,
           @PathVariable("eventId") @Positive Long eventId,
           @Valid @RequestBody InputCommentDto input) {
      return service.addNewComment(authorId, eventId, input);
   }

   /*
   Обновление комментария пользователем
    */
   @ResponseStatus(HttpStatus.OK)
   @PatchMapping(path = "/{id}")
   public CommentDto updateCommentByAuthor(
           @PathVariable("userId") @Positive Long authorId,
           @PathVariable("id") @Positive Long id,
           @Valid @RequestBody InputCommentDto input) {
      return service.updateCommentByAuthor(authorId, id, input);
   }

   /*
   Удаление комментария автором
    */
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @DeleteMapping(path = "/{id}")
   public void removeCommentByAuthor(
           @PathVariable("userId") @Positive Long authorId,
           @PathVariable("id") @Positive Long id) {
      service.removeCommentByAuthor(authorId, id);
   }

   /*
   Получение списка комментариев пользователя
   фильтр по: событиям, периоду, тексту
    */
   @ResponseStatus(HttpStatus.OK)
   @GetMapping
   public Collection<CommentShortDto> getUserComments(
           @PathVariable("userId") @Positive Long authorId,
           @Valid CommentFilter filter) {
      return service.getUserComments(authorId, filter);
   }

   @ResponseStatus(HttpStatus.OK)
   @GetMapping(path = "/{id}")
   public CommentDto getUserComment(
           @PathVariable("userId") @Positive Long authorId,
           @PathVariable("id") @Positive Long commentId) {
      return service.getUserComment(authorId, commentId);
   }
}
