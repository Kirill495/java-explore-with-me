package ru.practicum.ewm.main_service.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.event.dto.EventFullDto;
import ru.practicum.ewm.main_service.event.dto.EventShortDto;
import ru.practicum.ewm.main_service.event.dto.NewEventDto;
import ru.practicum.ewm.main_service.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.main_service.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class, CategoryMapper.class})
public interface EventMapper {

   @Mapping(source = "location.lat", target = "locationLat")
   @Mapping(source = "location.lon", target = "locationLon")
   EventEntity toEntity(Event model);

   @Mapping(source = "locationLat", target = "location.lat")
   @Mapping(source = "locationLon", target = "location.lon")
   @Mapping(target = "confirmedRequests", constant = "0L")
   @Mapping(target = "views", constant = "0")
   Event toModel(EventEntity entity);

   @Mapping(target = "category", source = "category", ignore = true)
   @Mapping(target = "participantLimit", source = "participantLimit", defaultValue = "0")
   @Mapping(target = "state", constant = "PENDING")
   @Mapping(target = "confirmedRequests", constant = "0L")
   @Mapping(target = "views", constant = "0")
   Event toModel(NewEventDto eventDto);

   @Mapping(target = "category", source = "category", ignore = true)
   @Mapping(target = ".", source = "stateAction", ignore = true)
   @Mapping(target = "confirmedRequests", constant = "0L")
   @Mapping(target = "views", constant = "0")
   Event toModel(UpdateEventAdminRequest request);

   @Mapping(target = "category", source = "category", ignore = true)
   @Mapping(target = ".", source = "stateAction", ignore = true)
   Event toModel(UpdateEventUserRequest request);


   EventFullDto toFullDto(Event model);

   List<EventFullDto> toFullDto(List<Event> models);

   EventShortDto toShortDto(Event model);

   List<EventShortDto> toShortDto(List<Event> models);

   List<Event> toModel(List<EventEntity> entities);

   Collection<EventEntity> toEntity(Collection<Event> models);

}
