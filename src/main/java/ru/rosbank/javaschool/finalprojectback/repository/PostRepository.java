package ru.rosbank.javaschool.finalprojectback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findAllByContentLikeIgnoreCase(String q);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likes = p.likes + :increment WHERE p.id = :id")
    void increaseLikesById(@Param("id") int id, @Param("increment") int increment);

    @Modifying
    @Query("UPDATE PostEntity p SET p.likes = p.likes - :decrement WHERE p.id = :id")
    void decreaseLikesById(@Param("id") int id, @Param("decrement") int decrement);

    @Modifying
    @Query("UPDATE PostEntity p SET p.removed=true WHERE p.id = :id")
    void setRemovedById(@Param("id") int id);

}