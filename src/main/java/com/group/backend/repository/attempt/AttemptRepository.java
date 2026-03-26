package com.group.backend.repository.attempt;

import com.group.backend.entity.Attempt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

  List<Attempt> findAllByUser_IdOrderByStartTimeDesc(Integer userId);

  @Query("SELECT a FROM Attempt a JOIN FETCH a.exam WHERE a.user.id = :userId")
  List<Attempt> findByUserIdWithExam(@Param("userId") Integer userId);
}
