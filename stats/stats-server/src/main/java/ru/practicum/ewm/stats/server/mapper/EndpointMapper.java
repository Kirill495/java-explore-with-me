package ru.practicum.ewm.stats.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.stats.server.dto.EndpointHit;
import ru.practicum.ewm.stats.server.entity.EndpointHitEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EndpointMapper {

   EndpointMapper MAPPER = Mappers.getMapper(EndpointMapper.class);

   EndpointHitEntity toEntity(EndpointHit endpointHit);

   EndpointHit toDto(EndpointHitEntity entity);
}
