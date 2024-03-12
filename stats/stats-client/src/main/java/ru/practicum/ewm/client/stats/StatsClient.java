package ru.practicum.ewm.client.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.server.dto.EndpointHit;
import ru.practicum.ewm.stats.server.dto.EndpointHitDto;
import ru.practicum.ewm.stats.server.dto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   @Autowired
   public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
      super(
              builder
                      .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                      .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                      .build()
      );
   }

   public void saveInfo(EndpointHitDto endpointHit) {
      ResponseEntity<Object> result = post("/hit", endpointHit);
   }

   public ResponseEntity<List<ViewStats>> fetchInfo(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
      String path = "/stats?start={start}&end={end}&unique={unique}";
      Map<String, Object> params = new HashMap<>();
      params.put("start",  start.format(FORMATTER));
      params.put("end", end.format(FORMATTER));
      params.put("unique", unique);
      if (uris != null) {
         int uriIndex = 0;
         for (String uri : uris) {
            path += "&uris={uri" + uriIndex + "}";
            params.put("uri" + uriIndex++, uri);
         }
      }
      ResponseEntity<Object> response = get(path, params);
      return new ResponseEntity<>((List<ViewStats>) response.getBody(), response.getStatusCode());
   }
}
