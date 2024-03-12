package ru.practicum.ewm.main_service.participation.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main_service.event.entity.EventEntity;
import ru.practicum.ewm.main_service.event.entity.RequestsToEvent;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
import ru.practicum.ewm.main_service.participation.storage.entity.RequestEntity;

import java.util.Collection;

public interface ParticipationRequestRepository extends JpaRepository<RequestEntity, Long>,
        JpaSpecificationExecutor<RequestEntity> {

   @Query("SELECT " +
           "new ru.practicum.ewm.main_service.event.entity.RequestsToEvent(r.event, COUNT(r)) " +
           "FROM " +
           "   RequestEntity AS r " +
           "WHERE " +
           "   r.event in (:events) " +
           "   AND r.status = :status_confirmed " +
           "GROUP BY " +
           "   r.event")
   @Transactional(readOnly = true)
   Collection<RequestsToEvent> countRequestsToEvents(@Param("events") Collection<EventEntity> events, @Param(("status_confirmed"))RequestStatus status);
}
