package ru.practicum.ewm.main_service.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.dto.UserInputDto;
import ru.practicum.ewm.main_service.user.exception.CreateUserException;
import ru.practicum.ewm.main_service.user.exception.UserNotFoundException;
import ru.practicum.ewm.main_service.user.mapper.UserMapper;
import ru.practicum.ewm.main_service.user.model.User;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;
import ru.practicum.ewm.main_service.user.storage.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository repository;
   private final UserMapper mapper;

   @Override
   public UserDto addUser(UserInputDto userDto) {
      if (repository.exists((root, query, builder) -> builder.equal(root.get("email"), userDto.getEmail()))) {
         throw new CreateUserException(String.format("user with email=%s already exists", userDto.getEmail()));
      }
      return mapper.toOutputDto(mapper.toModel(repository.save(mapper.toEntity(mapper.toModel(userDto)))));
   }

   @Override
   public List<UserDto> getUsers(List<Long> ids, Pageable page) {

      if ((ids == null) || ids.isEmpty()) {
         return mapper.toOutputDto(mapper.toModel(repository.findAll(page).toList()));
      } else {
         return mapper.toOutputDto(mapper.toModel(repository.findAllById(ids)));
      }
   }

   @Override
   public User getUserUtil(long id) {
      UserEntity user = repository.findById(id)
              .orElseThrow(() -> new UserNotFoundException(id));
      return mapper.toModel(user);
   }

   @Override
   public void removeUser(long id) {
      try {
         repository.deleteById(id);
      } catch (EmptyResultDataAccessException e) {
         throw new UserNotFoundException(id);
      }
   }
}
