package ru.practicum.ewm.main_service.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main_service.compilation.storage.entity.CompilationEntity;
import ru.practicum.ewm.main_service.compilation.exception.CompilationNotFoundException;
import ru.practicum.ewm.main_service.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.compilation.storage.repository.CompilationRepository;
import ru.practicum.ewm.main_service.compilation.storage.specification.CompilationSpecification;
import ru.practicum.ewm.main_service.event.model.Event;
import ru.practicum.ewm.main_service.event.service.EventService;
import ru.practicum.ewm.main_service.filter.BaseFilter;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

   private final CompilationRepository repository;
   private final EventService eventService;
   private final CompilationMapper mapper;

   @Override
   @Transactional
   public CompilationDto createCompilation(NewCompilationDto input) {

      Compilation compilation = mapper.toModel(input);
      appendEvents(compilation, input.getEvents());
      CompilationEntity entity = repository.save(mapper.toEntity(compilation));
      compilation.setId(entity.getId());
      return mapper.toDto(compilation);
   }

   @Override
   @Transactional
   public CompilationDto updateCompilation(long id, UpdateCompilationDto input) {
      Compilation existingComp = findById(id);
      Compilation updComp = mapper.toModel(input);
      appendEvents(updComp, input.getEvents());

      existingComp.setEvents(updComp.getEvents());
      if (updComp.getPinned() != null) {
         existingComp.setPinned(updComp.getPinned());
      }
      if (updComp.getTitle() != null && !updComp.getTitle().isBlank()) {
         existingComp.setTitle(updComp.getTitle());
      }

      repository.save(mapper.toEntity(existingComp));
      appendStatsToCompilationEvents(existingComp);
      return mapper.toDto(existingComp);
   }

   @Override
   @Transactional
   public void removeCompilation(long id) {
      repository.deleteById(id);
   }

   @Override
   @Transactional(readOnly = true)
   public CompilationDto getCompilation(long id) {
      Compilation compilation = findById(id);
      appendStatsToCompilationEvents(compilation);
      return mapper.toDto(compilation);
   }

   @Override
   @Transactional(readOnly = true)
   public List<CompilationDto> getCompilations(boolean pinned, BaseFilter filter) {

      Pageable pageable = PageRequest.of(filter.getFrom() / filter.getSize(), filter.getSize());
      List<Compilation> compilations = mapper.toModel(
              repository.findAll(CompilationSpecification.isPinned(pinned), pageable).getContent());
      appendStatsToCompilations(compilations);

      return mapper.toDto(compilations);
   }

   private Compilation findById(long id) {
      return mapper.toModel(repository.findById(id).orElseThrow(() -> new CompilationNotFoundException(id)));
   }

   private void appendEvents(Compilation compilation, List<Long> eventIds) {
      compilation.setEvents(getEvents(eventIds));
   }

   private List<Event> getEvents(List<Long> eventIds) {
      return (eventIds == null || eventIds.isEmpty()) ? Collections.emptyList() : eventService.getEventsByIds(eventIds);
   }

   private void appendStatsToCompilations(List<Compilation> compilations) {
      Set<Event> events = compilations.stream()
              .flatMap(compilation -> (compilation.getEvents()).stream())
              .collect(Collectors.toSet());
      eventService.appendStats(events);
   }

   private void appendStatsToCompilationEvents(Compilation compilation) {
      eventService.appendStats(compilation.getEvents());
   }
}
