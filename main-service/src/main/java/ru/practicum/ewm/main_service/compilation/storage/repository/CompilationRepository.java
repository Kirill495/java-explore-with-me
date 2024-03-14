package ru.practicum.ewm.main_service.compilation.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewm.main_service.compilation.storage.entity.CompilationEntity;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long>,
        JpaSpecificationExecutor<CompilationEntity> {
}
