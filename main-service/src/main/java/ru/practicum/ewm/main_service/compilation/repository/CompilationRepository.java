package ru.practicum.ewm.main_service.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main_service.compilation.entity.CompilationEntity;

import java.util.List;

public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {

   List<CompilationEntity> findAllByPinned(boolean pinned, Pageable pageable);
}
