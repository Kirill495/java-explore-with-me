package ru.practicum.ewm.main_service.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.compilation.dto.CompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main_service.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.main_service.compilation.entity.CompilationEntity;
import ru.practicum.ewm.main_service.compilation.model.Compilation;
import ru.practicum.ewm.main_service.event.mapper.EventMapper;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EventMapper.class})
public interface CompilationMapper {

   @Mapping(target = "events", source = ".", ignore = true)
   Compilation toModel(NewCompilationDto input);


   @Mapping(target = "events", source = ".", ignore = true)
   Compilation toModel(UpdateCompilationDto input);

   Compilation toModel(CompilationEntity entity);

   List<Compilation> toModel(List<CompilationEntity> entities);

   CompilationEntity toEntity(Compilation model);

   CompilationDto toDto(Compilation model);

   List<CompilationDto> toDto(List<Compilation> models);
}
