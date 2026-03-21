package com.group.backend.repository.attempt;

import com.group.backend.entity.Attempt;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

  List<Attempt> findAllByUserIdOrderByStartTimeDesc(Integer userId);
}
