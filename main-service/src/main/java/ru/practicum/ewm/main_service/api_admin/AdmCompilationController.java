package ru.practicum.ewm.main_service.api_admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main_service.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdmCompilationController {

   private final CompilationService service;

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto input) {
      return service.createCompilation(input);
   }

   @DeleteMapping("/{compilationId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteCompilation(@PathVariable @Positive long id) {
      service.removeCompilation(id);
   }

   @PatchMapping("/{compilationId}")
   public CompilationDto updateCompilation(
           @PathVariable long id,
           @RequestBody @Valid UpdateCompilationDto input) {
      return service.updateCompilation(id, input);
   }

}
