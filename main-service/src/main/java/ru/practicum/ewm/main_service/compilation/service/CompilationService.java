package ru.practicum.ewm.main_service.compilation.service;

import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main_service.filter.BaseFilter;

import java.util.List;

public interface CompilationService {

   CompilationDto createCompilation(NewCompilationDto input);

   CompilationDto updateCompilation(Long id, UpdateCompilationDto input);

   void removeCompilation(Long id);

   CompilationDto getCompilation(long id);

   List<CompilationDto> getCompilations(boolean pinned, BaseFilter filter);
}
