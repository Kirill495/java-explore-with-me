package ru.practicum.ewm.main_service.user.storage.service;

import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.dto.UserInputDto;
import ru.practicum.ewm.main_service.user.model.User;

import java.util.List;

public interface UserService {
   UserDto addUser(UserInputDto userDto);

   List<UserDto> getUsers(List<Long> ids, long from, int size);

   User getUserUtil(long id);

   void removeUser(long id);
}
