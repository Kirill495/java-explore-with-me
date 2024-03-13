package ru.practicum.ewm.stats.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.ewm.stats.server.entity.EndpointHitEntity;
import ru.practicum.ewm.stats.server.exception.IncorrectParameterException;
import ru.practicum.ewm.stats.server.mapper.EndpointMapper;
import ru.practicum.ewm.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

   private final StatsRepository statsRepository;
   private final EndpointMapper mapper;

   @Override
   public EndpointHit addEndpointHit(EndpointHit endpointHit) {
      EndpointHitEntity savedEndpoint = statsRepository.save(mapper.toEntity(endpointHit));
      log.info("saved endpoint: {}", savedEndpoint);
      return mapper.toDto(savedEndpoint);
   }

   @Override
   public List<ViewStats> getEndpointHitStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
      if (!start.isBefore(end)) {
         throw new IncorrectParameterException("start date must be before end date");
      }
      if (unique) {
         return statsRepository.findUniqueEndpointHitsStats(start, end, uris, (uris == null || uris.isEmpty()));
      } else {
         return statsRepository.findAllEndpointHitsStats(start, end, uris, (uris == null || uris.isEmpty()));
      }
   }
}
