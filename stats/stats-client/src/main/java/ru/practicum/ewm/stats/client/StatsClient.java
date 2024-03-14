package ru.practicum.ewm.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.dto.EndpointHitDto;
import ru.practicum.ewm.stats.dto.ViewStats;
import static ru.practicum.ewm.stats.env.Environments.FORMATTER;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Service
public class StatsClient {

   private final RestTemplate rest;
   private static final String FETCH_PATH_TEMPLATE = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
   private static final String SAVE_PATH = "/hit";

   @Autowired
   public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
      this(builder
              .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
              .requestFactory(HttpComponentsClientHttpRequestFactory::new)
              .build());
   }

   public StatsClient(RestTemplate rest) {
      this.rest = rest;
   }

   public void saveInfo(EndpointHitDto endpointHit) {
      makeAndSendRequest(
              HttpMethod.POST,
              SAVE_PATH,
              null,
              endpointHit,
              new ParameterizedTypeReference<>() {}
      );
   }

   public ResponseEntity<List<ViewStats>> fetchInfo(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
      Map<String, Object> params = Map.of(
              "start",  start.format(FORMATTER),
              "end",    end.format(FORMATTER),
              "uris",   uris.toArray(),
              "unique", unique);
      return makeAndSendRequest(
              HttpMethod.GET,
              FETCH_PATH_TEMPLATE,
              params,
              null,
              new ParameterizedTypeReference<>() {}
      );
   }

   private <T> ResponseEntity<T> makeAndSendRequest(
           HttpMethod method,
           String path,
           @Nullable Map<String, Object> parameters,
           @Nullable Object body,
           ParameterizedTypeReference<T> typeReference) {

      HttpEntity<Object> requestEntity = new HttpEntity<>(body, defaultHeaders());
      ResponseEntity<T> statsServerResponse;
      try {
         if (parameters != null) {
            statsServerResponse = rest.exchange(path, method, requestEntity, typeReference, parameters);
         } else {
            statsServerResponse = rest.exchange(path, method, requestEntity, typeReference);
         }
      } catch (HttpStatusCodeException e) {
         return ResponseEntity.status(e.getStatusCode()).build();
      }
      return prepareGatewayResponse(statsServerResponse);
   }

   private HttpHeaders defaultHeaders() {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(List.of(MediaType.APPLICATION_JSON));
      return headers;
   }

   private static <T> ResponseEntity<T> prepareGatewayResponse(ResponseEntity<T> response) {
      if (response.getStatusCode().is2xxSuccessful()) {
         return response;
      }
      ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
      if (response.hasBody()) {
         return responseBuilder.body(response.getBody());
      }
      return responseBuilder.build();
   }
}
