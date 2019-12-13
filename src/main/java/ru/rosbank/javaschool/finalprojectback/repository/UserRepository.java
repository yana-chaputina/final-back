package ru.rosbank.javaschool.finalprojectback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsById(long id);

    boolean existsByUsername(String username);
}
