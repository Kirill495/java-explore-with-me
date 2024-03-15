package ru.practicum.ewm.main_service.user.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>,
                                        JpaSpecificationExecutor<UserEntity> {

}
