package ru.practicum.ewm.main_service.user.storage.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<UserEntity> {

   @Override
   public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      return null;
//      final List<Predicate> predicates = new ArrayList<>();

   }
}
