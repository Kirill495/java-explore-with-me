package ru.practicum.ewm.main_service.event.storage.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.filter.AdminEventFilter;
import ru.practicum.ewm.main_service.filter.PublicEventFilter;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
import ru.practicum.ewm.main_service.participation.storage.entity.RequestEntity;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class EventSpecification {

   public static Specification<EventEntity> ofFilter(AdminEventFilter filter) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new LinkedList<>();
         if (Objects.nonNull(filter.getUsers()) && !filter.getUsers().isEmpty())
            predicates.add(root.get("initiator").get("id").in(filter.getUsers()));
         if (Objects.nonNull(filter.getStates()) && !filter.getStates().isEmpty())
            predicates.add(root.get("state").in(filter.getStates()));
         if (Objects.nonNull(filter.getCategories()) && !filter.getCategories().isEmpty())
            predicates.add(root.get("category").get("id").in(filter.getCategories()));
         if (Objects.nonNull(filter.getRangeStart()))
            predicates.add(builder.greaterThanOrEqualTo(root.get("eventDate"), filter.getRangeStart()));
         if (Objects.nonNull(filter.getRangeEnd()))
            predicates.add(builder.lessThanOrEqualTo(root.get("eventDate"), filter.getRangeEnd()));
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<EventEntity> ofInitiatorAndEventId(Long initiatorId, Long eventId) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         predicates.add(builder.equal(root.get("initiator").get("id"), initiatorId));
         predicates.add(builder.equal(root.get("id"), eventId));
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<EventEntity> ofEventIdAndPublished(Long eventId) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         predicates.add(builder.equal(root.get("state"), EventState.PUBLISHED));
         predicates.add(builder.equal(root.get("id"), eventId));
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<EventEntity> ofCategory(Long categoryId) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         predicates.add(builder.equal(root.get("category").get("id"), categoryId));
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<EventEntity> ofInitiator(Long initiatorId) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         predicates.add(builder.equal(root.get("initiator").get("id"), initiatorId));
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<EventEntity> ofFilter(PublicEventFilter filter) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         if (Objects.nonNull(filter.getText()) && !filter.getText().trim().isEmpty())
            predicates.add(builder.or(
                    builder.like(root.get("annotation"), '%' + filter.getText() + '%'),
                    builder.like(root.get("description"), '%' + filter.getText() + '%')
            ));
         if (Objects.nonNull(filter.getCategories()) && !filter.getCategories().isEmpty())
            predicates.add(root.get("category").get("id").in(filter.getCategories()));
         if (Objects.nonNull(filter.getPaid()))
            predicates.add(builder.equal(root.get("paid"), filter.getPaid()));
         if (Objects.nonNull(filter.getRangeStart()))
            predicates.add(builder.greaterThanOrEqualTo(root.get("eventDate"), filter.getRangeStart()));
         if (Objects.nonNull(filter.getRangeEnd()))
            predicates.add(builder.lessThanOrEqualTo(root.get("eventDate"), filter.getRangeEnd()));
         if (Objects.nonNull(filter.getOnlyAvailable()) && filter.getOnlyAvailable()) {
            Subquery<Long> sub = query.subquery(Long.class);
            Root<RequestEntity> requestRoot = sub.from(RequestEntity.class);
            sub.select(builder.count(requestRoot)).where(builder.and(
                    builder.equal(requestRoot.get("event").get("id"), root.get("id")),
                    builder.equal(requestRoot.get("status"), RequestStatus.CONFIRMED.name())
            ));
            predicates.add(builder.greaterThan(root.get("participantLimit"), sub));
         }
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

}
