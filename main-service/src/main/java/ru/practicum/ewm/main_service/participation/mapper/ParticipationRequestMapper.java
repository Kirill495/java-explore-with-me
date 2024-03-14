package ru.practicum.ewm.main_service.participation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;
import ru.practicum.ewm.main_service.participation.dto.ParticipationRequestDto;
import ru.practicum.ewm.main_service.participation.model.Request;
import ru.practicum.ewm.main_service.participation.storage.entity.RequestEntity;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, EventMapper.class})
public interface ParticipationRequestMapper {

   Request toModel(RequestEntity entity);

   RequestEntity toEntity(Request model);

   List<RequestEntity> toEntity(List<Request> model);

   @Mapping(target = "requester", source = "requester.id")
   @Mapping(target = "event", source = "event.id")
   ParticipationRequestDto toDto(Request model);

   List<ParticipationRequestDto> toDto(List<Request> models);

   List<Request> toModel(List<RequestEntity> entities);
}
