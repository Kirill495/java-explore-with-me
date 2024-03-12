package ru.practicum.ewm.stats.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.server.dto.EndpointHit;
import ru.practicum.ewm.stats.server.dto.ViewStats;
import ru.practicum.ewm.stats.server.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StatsController {

   private final StatsService service;
   private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

   @PostMapping(path = "/hit", consumes = "application/json")
   public ResponseEntity<EndpointHit> saveInfo(@RequestBody @Valid EndpointHit endpointHit) {
      return new ResponseEntity<>(service.addEndpointHit(endpointHit), HttpStatus.CREATED);
   }

   @GetMapping(path = "/stats", produces = "application/json")
   @ResponseBody
   public ResponseEntity<List<ViewStats>> fetchInfo(
           @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
           @RequestParam @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
           @RequestParam(name = "uris", required = false) List<String> uris,
           @RequestParam(defaultValue = "false") boolean unique) {

      return new ResponseEntity<>(service.getEndpointHitStat(start, end, uris, unique), HttpStatus.OK);
   }
}
