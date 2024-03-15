package ru.practicum.ewm.main_service.comment.storage.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.main_service.comment.storage.entity.CommentEntity;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.comment.filter.CommentFilter;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentSpecification {

   public static Specification<CommentEntity> ofFilter(CommentFilter filter) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         if ((filter.getText() != null) && !filter.getText().isBlank()) {
            predicates.add(builder.like(root.get("text"), '%' + filter.getText() + '%'));
         }
         if ((filter.getEvents() != null) && !filter.getEvents().isEmpty()) {
            predicates.add(root.get("event").in(filter.getEvents()));
         }
         if (filter.getRangeStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdOn"), filter.getRangeStart()));
         }
         if (filter.getRangeEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("createdOn"), filter.getRangeEnd()));
         }
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }

   public static Specification<CommentEntity> ofAuthor(Long authorId) {
      return (root, query, builder) -> builder.equal(root.get("author").get("id"), authorId);
   }

   public static Specification<CommentEntity> ofId(UserEntity author) {
      return (root, query, builder) -> builder.equal(root.get("author"), author);
   }

   public static Specification<CommentEntity> ofEvent(Long eventId) {
      return (root, query, builder) -> builder.equal(root.get("event").get("id"), eventId);
   }

   public static Specification<CommentEntity> ofOnlyPublishedEvents() {
      return (root, query, builder) -> builder.equal(root.get("event").get("state"), EventState.PUBLISHED.name());
   }
}
