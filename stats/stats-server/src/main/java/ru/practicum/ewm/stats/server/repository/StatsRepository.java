package ru.practicum.ewm.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.server.dto.ViewStats;
import ru.practicum.ewm.stats.server.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHitEntity, Integer>, JpaSpecificationExecutor<EndpointHitEntity> {

   @Query("SELECT " +
           "new ru.practicum.ewm.stats.server.dto.ViewStats(e.app, e.uri, COUNT(e)) " +
           "FROM EndpointHitEntity AS e " +
           "WHERE timestamp BETWEEN :start AND :end " +
           "   AND (e.uri IN (:uris) OR :no_uri_filter = TRUE) " +
           "GROUP BY e.app, e.uri " +
           "ORDER BY COUNT(e) DESC")
   @Transactional(readOnly = true)
   List<ViewStats> findAllEndpointHitsStats(
           @Param("start") LocalDateTime start,
           @Param("end") LocalDateTime end,
           @Param("uris") List<String> uris,
           @Param("no_uri_filter") boolean uriFilter);

   @Query("SELECT " +
           "new ru.practicum.ewm.stats.server.dto.ViewStats(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
           "FROM EndpointHitEntity AS e " +
           "WHERE timestamp BETWEEN :start AND :end " +
           "   AND (e.uri IN (:uris) OR :no_uri_filter = TRUE) " +
           "GROUP BY e.app, e.uri " +
           "ORDER BY COUNT(DISTINCT e.ip) DESC")
   @Transactional(readOnly = true)
   List<ViewStats> findUniqueEndpointHitsStats(
           @Param("start") LocalDateTime start,
           @Param("end") LocalDateTime end,
           @Param("uris") List<String> uris,
           @Param("no_uri_filter") boolean uriFilter);
}
