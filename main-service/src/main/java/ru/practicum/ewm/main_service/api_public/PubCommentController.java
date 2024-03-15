package ru.practicum.ewm.main_service.api_public;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.comment.dto.CommentShortDto;
import ru.practicum.ewm.main_service.comment.service.CommentService;
import ru.practicum.ewm.main_service.filter.BaseFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;

@SuppressWarnings("unused")
@Validated
@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PubCommentController {
   private final CommentService service;

   @GetMapping
   public Collection<CommentShortDto> getAllCommentsToEvent(
           @PathVariable @Positive long eventId,
           @Valid BaseFilter filter) {
      return service.getAllCommentsToEvent(eventId, filter);
   }
}
