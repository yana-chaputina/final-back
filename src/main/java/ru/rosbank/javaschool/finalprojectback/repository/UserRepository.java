package ru.rosbank.javaschool.finalprojectback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rosbank.javaschool.finalprojectback.dto.UserProfileResponseDto;
import ru.rosbank.javaschool.finalprojectback.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsById(long id);

    boolean existsByUsername(String username);

    List<UserEntity> findAllByUsernameIs(String q);


    @Modifying
    @Query("UPDATE UserEntity u SET u.enabled=false WHERE u.id = :id")
    void setRemovedById(@Param("id") int id);
}
