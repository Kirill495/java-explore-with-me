package ru.practicum.ewm.main_service.comment.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewm.main_service.comment.storage.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>,
        JpaSpecificationExecutor<CommentEntity> {
}
