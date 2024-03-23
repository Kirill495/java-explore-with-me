package ru.practicum.ewm.main_service.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.dto.CommentShortDto;
import ru.practicum.ewm.main_service.comment.dto.InputCommentDto;
import ru.practicum.ewm.main_service.comment.exception.CommentNotFoundException;
import ru.practicum.ewm.main_service.comment.exception.CommentUpdateException;
import ru.practicum.ewm.main_service.comment.exception.NewCommentException;
import ru.practicum.ewm.main_service.comment.mapper.CommentMapper;
import ru.practicum.ewm.main_service.comment.model.Comment;
import ru.practicum.ewm.main_service.comment.storage.entity.CommentEntity;
import ru.practicum.ewm.main_service.comment.storage.repository.CommentRepository;
import ru.practicum.ewm.main_service.comment.storage.specification.CommentSpecification;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.event.service.EventService;
import ru.practicum.ewm.main_service.exception.OperationRequirementsMismatchException;
import ru.practicum.ewm.main_service.filter.BaseFilter;
import ru.practicum.ewm.main_service.comment.filter.CommentFilter;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.service.UserService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

   private final CommentMapper mapper;
   private final CommentRepository repository;
   private final UserService userService;
   private final EventService eventService;

   @Override
   @Transactional
   public CommentDto addNewComment(Long authorId, Long eventId, InputCommentDto input) {
      Event event = eventService.getEventUtil(eventId);
      User author = userService.getUserUtil(authorId);
      if (!event.getState().equals(EventState.PUBLISHED)) {
         throw new NewCommentException("Only published events can be commented");
      }

      Comment comment = mapper.toModel(input);
      comment.setAuthor(author);
      comment.setEvent(event);
      CommentEntity savedCommentEntity = repository.save(mapper.toEntity(comment));
      Comment savedComment = mapper.toModel(savedCommentEntity);
      eventService.appendStats(savedComment.getEvent());
      return mapper.toDto(savedComment);
   }

   @Override
   @Transactional
   public CommentDto updateCommentByAuthor(Long userId, Long commentId, InputCommentDto input) {

      Comment comment = findCommentUtil(commentId);
      User author = userService.getUserUtil(userId);
      if (!comment.getAuthor().equals(author)) {
         throw new CommentUpdateException(String.format("User with id=%d is not author of comment with id=%d", userId, commentId));
      }
      comment.setText(input.getText());
      Comment savedComment = mapper.toModel(repository.save(mapper.toEntity(comment)));
      eventService.appendStats(savedComment.getEvent());
      return mapper.toDto(savedComment);
   }

   @Override
   public CommentDto updateCommentByAdmin(Long commentId, InputCommentDto input) {
      Comment comment = findCommentUtil(commentId);
      comment.setText(input.getText());
      Comment savedComment = mapper.toModel(repository.save(mapper.toEntity(comment)));
      eventService.appendStats(savedComment.getEvent());
      return mapper.toDto(savedComment);
   }

   @Override
   @Transactional
   public void removeCommentByAuthor(long authorId, long commentId) {
      Comment comment = findCommentUtil(commentId);
      User author = userService.getUserUtil(authorId);
      if (!comment.getAuthor().equals(author)) {
         throw new CommentUpdateException(String.format("User with id=%d is not author of comment with id=%d", authorId, commentId));
      }
      repository.deleteById(commentId);
   }

   @Override
   public void removeCommentByAdmin(long commentId) {
      if (!repository.existsById(commentId)) {
         throw new CommentNotFoundException(commentId);
      }
      repository.deleteById(commentId);
   }

   @Override
   public Collection<CommentDto> getAllComments(CommentFilter filter) {
      return null;
   }

   @Override
   @Transactional(readOnly = true)
   public Collection<CommentShortDto> getUserComments(long authorId, CommentFilter filter) {
      User author = userService.getUserUtil(authorId);
      Pageable pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
      Collection<CommentEntity> entities = repository
              .findAll(CommentSpecification.ofFilter(filter)
                      .and(CommentSpecification.ofAuthor(author.getId())), pageable).getContent();
      return mapper.toShortDto(mapper.toModel(entities));
   }

   @Override
   public CommentDto getUserComment(long authorId, long commentId) {
      User author = userService.getUserUtil(authorId);
      CommentEntity entity = repository.findOne(CommentSpecification.ofAuthor(author.getId()).and(
              (root, query, builder) -> builder.equal(root.get("id"), commentId)))
              .orElseThrow(() -> new CommentNotFoundException(commentId));
      return mapper.toDto(mapper.toModel(entity));
   }

   @Override
   public Collection<CommentShortDto> getAllCommentsToEvent(long eventId, BaseFilter filter) {

      Event event = eventService.getEventUtil(eventId);
      if (!event.getState().equals(EventState.PUBLISHED)) {
         throw new OperationRequirementsMismatchException(String.format("Event with id=%d is not in PUBLISHED state", eventId));
      }
      Pageable pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
      Collection<CommentEntity> comments = repository.findAll(CommentSpecification.ofEvent(eventId), pageable).getContent();
      return mapper.toShortDto(mapper.toModel(comments));
   }

   private Comment findCommentUtil(long id) {
      return mapper.toModel(repository.findById(id).orElseThrow(() -> new CommentNotFoundException(id)));
   }


}
