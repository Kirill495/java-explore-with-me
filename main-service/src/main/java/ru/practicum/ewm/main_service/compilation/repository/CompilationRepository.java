package ru.practicum.ewm.main_service.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main_service.compilation.entity.CompilationEntity;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {
}
