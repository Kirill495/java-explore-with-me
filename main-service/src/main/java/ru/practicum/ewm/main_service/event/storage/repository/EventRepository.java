package ru.practicum.ewm.main_service.event.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main_service.event.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long>,
        JpaSpecificationExecutor<EventEntity> {

}
