package ru.practicum.ewm.main_service.api_admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.user.dto.UserDto;
import ru.practicum.ewm.main_service.user.dto.UserInputDto;
import ru.practicum.ewm.main_service.user.storage.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@Validated
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdmUserController {

   private final UserService service;

   @PostMapping(consumes = "application/json")
   public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserInputDto input) {

      return new ResponseEntity<>(service.addUser(input), HttpStatus.CREATED);
   }

   @GetMapping(produces = "application/json")
   ResponseEntity<List<UserDto>> getUsers(
           @RequestParam(name = "ids", required = false)  List<Long> ids,
           @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Long from,
           @RequestParam(name = "size", defaultValue = "10") @Positive int size) {

      return new ResponseEntity<>(service.getUsers(ids, from, size), HttpStatus.OK);
   }

   @DeleteMapping(path = "/{user-id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void removeUser(
           @PathVariable(name = "user-id") @Positive long userId) {
      service.removeUser(userId);
   }

}
