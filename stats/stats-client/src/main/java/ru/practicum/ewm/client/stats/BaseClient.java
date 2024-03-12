package ru.practicum.ewm.client.stats;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stats.server.dto.EndpointHit;
import ru.practicum.ewm.stats.server.dto.ViewStats;

import java.util.List;
import java.util.Map;

public class BaseClient {

   protected final RestTemplate rest;

   public BaseClient(RestTemplate rest) {
      this.rest = rest;
   }


   protected ResponseEntity<List<ViewStats>> getStats(
           String path,
           @Nullable Map<String, Object> parameters) {
      return makeAndSendRequest(
              HttpMethod.GET,
              path,
              parameters,
              null,
              new ParameterizedTypeReference<>() {}
      );
   }

   protected <T> ResponseEntity<EndpointHit> postEndpoint(String path, T body) {
      return makeAndSendRequest(
              HttpMethod.POST,
              path,
              null,
              body,
              new ParameterizedTypeReference<>() {}
      );
   }

   private <T> ResponseEntity<T> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable Object body, ParameterizedTypeReference<T> typeReference) {
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
