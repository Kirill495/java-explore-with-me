package ru.practicum.ewm.client.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stats.server.dto.EndpointHit;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "")
public class StatsController {

   private final StatsClient client;

   @PostMapping(path = "/hit", consumes = "application/json")
   @ResponseStatus(HttpStatus.CREATED)
   public ResponseEntity<Object> saveInfo(@RequestBody @Valid EndpointHit endpointHit) {
         return client.saveInfo(endpointHit);
   }

   @GetMapping(path = "/stats", produces = "application/json")
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public ResponseEntity<Object> fetchInfo(
           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
           @RequestParam(name = "uris", required = false) List<String> uris,
           @RequestParam(defaultValue = "false") boolean unique) {

      return client.fetchInfo(start, end, uris, unique);
   }
}
