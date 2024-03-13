package ru.practicum.ewm.stats.server.service;

import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
   EndpointHit addEndpointHit(EndpointHit endpointHit);

   List<ViewStats> getEndpointHitStat(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
