package ru.practicum.ewm.main_service.api_public;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.service.CompilationService;
import ru.practicum.ewm.main_service.filter.BaseFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PubCompilationController {

   private final CompilationService service;

   @GetMapping
   public List<CompilationDto> getCompilations(
           @Valid BaseFilter pageFilter,
           @RequestParam(defaultValue = "false") Boolean pinned
   ) {
      return service.getCompilations(pinned, pageFilter);
   }

   @GetMapping("/{id}")
   public CompilationDto getCompilationById(@PathVariable @Positive long id) {
      return service.getCompilation(id);
   }
}
