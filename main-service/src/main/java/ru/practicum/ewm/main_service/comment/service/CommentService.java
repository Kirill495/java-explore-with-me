package ru.practicum.ewm.main_service.comment.service;

import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.dto.CommentShortDto;
import ru.practicum.ewm.main_service.comment.dto.InputCommentDto;
import ru.practicum.ewm.main_service.filter.BaseFilter;
import ru.practicum.ewm.main_service.comment.filter.CommentFilter;

import java.util.Collection;

public interface CommentService {

   CommentDto addNewComment(Long authorId, Long eventId, InputCommentDto input);

   CommentDto updateCommentByAuthor(Long authorId, Long commentId, InputCommentDto input);

   CommentDto updateCommentByAdmin(Long commentId, InputCommentDto input);

   void removeCommentByAuthor(long authorId, long commentId);

   void removeCommentByAdmin(long commentId);

   Collection<CommentShortDto> getUserComments(long authorId, CommentFilter filter);

   CommentDto getUserComment(long authorId, long commentId);

   Collection<CommentShortDto> getAllCommentsToEvent(long eventId, BaseFilter filter);

   Collection<CommentDto> getAllComments(CommentFilter filter);
}
