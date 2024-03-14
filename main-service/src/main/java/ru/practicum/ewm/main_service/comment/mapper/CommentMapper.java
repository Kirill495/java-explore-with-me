package ru.practicum.ewm.main_service.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.comment.dto.CommentDto;
import ru.practicum.ewm.main_service.comment.dto.CommentShortDto;
import ru.practicum.ewm.main_service.comment.dto.InputCommentDto;
import ru.practicum.ewm.main_service.comment.model.Comment;
import ru.practicum.ewm.main_service.comment.storage.entity.CommentEntity;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EventMapper.class, UserMapper.class})
public interface CommentMapper {

   Comment toModel(InputCommentDto dto);

   Comment toModel(CommentEntity entity);

   Collection<Comment> toModel(Collection<CommentEntity> entities);

   CommentDto toDto(Comment model);

   Collection<CommentDto> toDto(Collection<Comment> models);

   @Mapping(target = "event", source = "event.id")
   @Mapping(target = "author", source = "author.id")
   CommentShortDto toShortDto(Comment model);

   Collection<CommentShortDto> toShortDto(Collection<Comment> models);

   CommentEntity toEntity(Comment model);

}
