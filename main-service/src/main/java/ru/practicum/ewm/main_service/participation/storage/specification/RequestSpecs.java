package ru.practicum.ewm.main_service.participation.storage.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.main_service.event.entity.EventEntity;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
import ru.practicum.ewm.main_service.participation.storage.entity.RequestEntity;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

import java.util.Objects;

@UtilityClass
public class RequestSpecs {

   public static Specification<RequestEntity> ofRequester(UserEntity requester) {
      if (Objects.isNull(requester)) {
         return null;
      }
      return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("requester"), requester));
   }

   public static Specification<RequestEntity> ofEvent(EventEntity event) {
      if (Objects.isNull(event)) {
         return null;
      }
      return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("event"), event));
   }

   public static Specification<RequestEntity> ofStatus(RequestStatus status) {
      if(Objects.isNull(status)) {
         return null;
      }
      return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
   }
}
