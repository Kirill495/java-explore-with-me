package ru.practicum.ewm.main_service.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.user.dto.UserInputDto;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.dto.UserShortDto;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;
import ru.practicum.ewm.main_service.user.model.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

   User toModel(UserInputDto input);

   User toModel(UserEntity entity);

   List<User> toModel(List<UserEntity> entities);

   UserDto toOutputDto(User model);

   List<UserDto> toOutputDto(List<User> models);

   UserEntity toEntity(User user);

   UserShortDto toShortDto(User model);

}
