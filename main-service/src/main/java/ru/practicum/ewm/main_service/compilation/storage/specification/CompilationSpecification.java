package ru.practicum.ewm.main_service.compilation.storage.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.main_service.compilation.storage.entity.CompilationEntity;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CompilationSpecification {
   public static Specification<CompilationEntity> isPinned(boolean pinned) {
      return (root, query, builder) -> {
         List<Predicate> predicates = new ArrayList<>();
         if (pinned) {
            predicates.add(builder.equal(root.get("pinned"), true));
         }
         return builder.and(predicates.toArray(Predicate[]::new));
      };
   }
}
